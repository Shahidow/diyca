package com.example.diyca.domain.startup.impl

import android.content.Context
import android.util.Log
import com.example.diyca.data.prefs.UserPrefsRepository
import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.data.repository.dictionaries.DictionaryNetworkRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.domain.startup.LibraryKeys
import com.example.diyca.domain.startup.StartupInteractor
import com.example.diyca.util.LANGUAGE_ID
import com.example.diyca.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class StartupInteractorImpl(
    private val context: Context,
    private val dictionaryNetworkRepository: DictionaryNetworkRepository,
    private val dictionaryDataBaseRepository: DictionaryDataBaseRepository,
    private val userNetworkRepository: UserNetworkRepository,
    private val userDataBaseRepository: UserDataBaseRepository,
    private val prefs: UserPrefsRepository,
    private val externalScope: CoroutineScope,
    okHttpClient: OkHttpClient
) : StartupInteractor {

    private val downloadClient = okHttpClient.newBuilder()
        .dispatcher(
            okhttp3.Dispatcher().apply {
                maxRequests = 64
                maxRequestsPerHost = 32
            }
        )
        .build()

    private val downloadSemaphore = Semaphore(16)

    private fun imagesDir(subFolder: String): File =
        File(context.filesDir, "images/$subFolder").apply { mkdirs() }

    private suspend fun downloadImage(
        subFolder: String,
        id: String,
        imageUrl: String?
    ): String? = withContext(Dispatchers.IO) {
        if (imageUrl.isNullOrBlank()) return@withContext null
        val file = File(imagesDir(subFolder), "$id.png")
        if (file.exists() && file.length() > 0) {
            return@withContext file.absolutePath
        }
        try {
            val request = Request.Builder().url(imageUrl).build()
            downloadClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body
                body.byteStream()
                    .use { input -> file.outputStream().use { output -> input.copyTo(output) } }
            }
            file.absolutePath
        } catch (e: Exception) {
            Log.e("StartupInteractor", "Failed to download image ($subFolder/$id)", e)
            null
        }
    }

    override suspend fun checkVersion() = supervisorScope {
        launch {
            try {
                syncMissingImages()
            } catch (e: Exception) {
                Log.e("StartupInteractor", "Failed to sync missing images", e)
            }
        }
        LibraryKeys.all.forEach { libKey ->
            launch {
                try {
                    when (libKey) {
                        LibraryKeys.REWARDS -> syncIfOutdated(
                            libKey,
                            { userNetworkRepository.getAllRewards() },
                            { it?.version })

                        LibraryKeys.PHRASES -> syncIfOutdated(
                            libKey,
                            { dictionaryNetworkRepository.getPhrasebookVersion() },
                            { it })

                        LibraryKeys.WORDS -> syncIfOutdated(
                            libKey,
                            { dictionaryNetworkRepository.getVocabularyVersion() },
                            { it })

                        else -> {}
                    }
                } catch (e: Exception) {
                    Log.e("StartupInteractor", "Error syncing library: $libKey", e)
                }
            }
        }
    }

    private suspend fun <T> syncIfOutdated(
        libKey: LibraryKeys,
        fetchVersion: suspend () -> Resource<T>,
        extractVersion: (T?) -> Int?
    ) {
        val resource = fetchVersion()
        if (resource is Resource.Success) {
            val serverVersion = extractVersion(resource.data)
            if (serverVersion != null) {
                val localVersion = prefs.getLibVersions(libKey).first()
                if (localVersion == null || localVersion < serverVersion) {
                    downloadAndSave(libKey)
                }
            } else {
                Log.w(
                    "StartupInteractor",
                    "Server returned success but version is null for $libKey"
                )
            }
        }
    }

    private suspend fun downloadAndSave(libKey: LibraryKeys) {
        try {
            when (libKey) {
                LibraryKeys.REWARDS -> handleDownload(
                    libKey,
                    { userNetworkRepository.getAllRewards() },
                    { data ->
                        userDataBaseRepository.clearAllRewards()
                        data.rewards.forEach { userDataBaseRepository.insertReward(it) }
                        scheduleImageDownloads(
                            subFolder = "rewards",
                            ids = data.rewards.map { it.id to it.image }
                        ) { id, localPath ->
                            userDataBaseRepository.updateRewardImage(id, localPath)
                        }
                    },
                    { it.version }
                )

                LibraryKeys.PHRASES -> handleDownload(
                    libKey,
                    { dictionaryNetworkRepository.getPhrasebooks(LANGUAGE_ID) },
                    { data ->
                        dictionaryDataBaseRepository.clearDictionary(DictionaryType.PHRASEBOOK)
                        dictionaryDataBaseRepository.clearAllPhrasebooks()
                        data.phrasebookList.forEach {
                            dictionaryDataBaseRepository.insertPhrasebook(it)
                        }
                        data.phrasebookItems.forEach {
                            dictionaryDataBaseRepository.insertDictionaryItem(it)
                        }
                        scheduleImageDownloads(
                            subFolder = "phrasebook",
                            ids = data.phrasebookList.map { it.id to it.image }
                        ) { id, localPath ->
                            dictionaryDataBaseRepository.updatePhrasebookImage(id, localPath)
                        }
                    },
                    { it.version }
                )

                LibraryKeys.WORDS -> handleDownload(
                    libKey,
                    { dictionaryNetworkRepository.getVocabulary() },
                    { data ->
                        dictionaryDataBaseRepository.clearDictionary(DictionaryType.WORD)
                        data.words.forEach {
                            dictionaryDataBaseRepository.insertDictionaryItem(it)
                        }
                    },
                    { it.version }
                )

                else -> {}
            }
        } catch (e: Exception) {
            Log.e("StartupInteractor", "Error during downloadAndSave", e)
        }
    }

    private fun scheduleImageDownloads(
        subFolder: String,
        ids: List<Pair<String, String?>>,
        updateDb: suspend (id: String, localPath: String) -> Unit
    ) {
        externalScope.launch {
            ids.forEach { (id, remoteUrl) ->
                launch {
                    downloadSemaphore.withPermit {
                        val localPath = downloadImage(subFolder, id, remoteUrl)
                        if (localPath != null) {
                            try {
                                updateDb(id, localPath)
                            } catch (e: Exception) {
                                Log.e("StartupInteractor", "Failed to update image path for $id", e)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun <T> handleDownload(
        libKey: LibraryKeys,
        fetchData: suspend () -> Resource<T>,
        saveData: suspend (T) -> Unit,
        extractVersion: (T) -> Int?
    ) {
        val resource = fetchData()
        if (resource is Resource.Success) {
            val data = resource.data ?: return
            saveData(data)
            extractVersion(data)?.let { prefs.saveLibVersion(libKey, it) }
        }
    }

    private suspend fun syncMissingImages() {
        userDataBaseRepository.getAllRewards().first().let { rewards ->
            val missing = rewards.filter { it.image?.startsWith("http") == true }
            if (missing.isNotEmpty()) {
                scheduleImageDownloads("rewards", missing.map { it.id to it.image }) { id, path ->
                    userDataBaseRepository.updateRewardImage(id, path)
                }
            }
        }
        dictionaryDataBaseRepository.getPhrasebooks().first().let { phrasebooks ->
            val missing = phrasebooks.filter { it.image?.startsWith("http") == true }
            if (missing.isNotEmpty()) {
                scheduleImageDownloads(
                    "phrasebook",
                    missing.map { it.id to it.image }) { id, path ->
                    dictionaryDataBaseRepository.updatePhrasebookImage(id, path)
                }
            }
        }
    }
}