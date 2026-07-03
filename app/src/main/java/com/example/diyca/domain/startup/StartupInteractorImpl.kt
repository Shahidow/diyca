package com.example.diyca.domain.startup

import com.example.diyca.data.prefs.UserPrefsRepository
import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.data.repository.dictionaries.DictionaryNetworkRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.util.ErrorType
import com.example.diyca.util.LoadingStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StartupInteractorImpl(
    private val dictionaryNetworkRepository: DictionaryNetworkRepository,
    private val db: DictionaryDataBaseRepository,
    private val userNetworkRepository: UserNetworkRepository,
    private val userDataBaseRepository: UserDataBaseRepository,
    private val prefs: UserPrefsRepository
) : StartupInteractor {
    val data = listOf(
        DictionaryItem.PhrasebookItem(
            parentId = 1,
            id = 1,
            original = "Разговорник пример",
            translation = "Пример перевода в разговорнике\n- Пример применения 1\n- Пример применения 2",
            isFavorite = false,
            audio = null,
        ),
        DictionaryItem.PhrasebookItem(
            parentId = 1,
            id = 2,
            original = "Разговорник пример",
            translation = "Пример перевода в разговорнике\n- Пример применения 1\n- Пример применения 2",
            isFavorite = false,
            audio = null,
        ),
        DictionaryItem.PhrasebookItem(
            parentId = 1,
            id = 3,
            original = "Разговорник пример",
            translation = "Пример перевода в разговорнике\n- Пример применения 1\n- Пример применения 2",
            isFavorite = false,
            audio = null,
        ),
        DictionaryItem.PhrasebookItem(
            parentId = 1,
            id = 4,
            original = "Разговорник пример",
            translation = "Пример перевода в разговорнике\n- Пример применения 1\n- Пример применения 2",
            isFavorite = false,
            audio = null,
        ),
        DictionaryItem.PhrasebookItem(
            parentId = 1,
            id = 5,
            original = "Разговорник пример",
            translation = "Пример перевода в разговорнике\n- Пример применения 1\n- Пример применения 2",
            isFavorite = false,
            audio = null,
        ),

        DictionaryItem.Expression(
            id = 1,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Expression(
            id = 2,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Expression(
            id = 3,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Expression(
            id = 4,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Expression(
            id = 5,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),

        DictionaryItem.Proverb(
            id = 1,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Proverb(
            id = 2,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Proverb(
            id = 3,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Proverb(
            id = 4,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Proverb(
            id = 5,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),

        DictionaryItem.Word(
            id = 1,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 2,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 3,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 4,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 5,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 6,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 7,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 8,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 9,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),
        DictionaryItem.Word(
            id = 10,
            original = "Пример",
            translation = "Пример перевода",
            isFavorite = false,
            audio = null
        ),

        )

    override fun downloadAndSaveAll(): Flow<LoadingStatus> = flow {
        try {
            // Список библиотек для загрузки
            val libs = LibraryKeys.all

            libs.forEachIndexed { index, libKey ->
                val currentProgress = (index + 1).toFloat() / libs.size
                val message = when (libKey) {
                    LibraryKeys.WORDS -> "Загрузка слов"
                    LibraryKeys.PHRASES -> "Загрузка разговорника"
                    LibraryKeys.PROVERBS -> "Загрузка пословиц"
                    LibraryKeys.EXPRESSIONS -> "Загрузка идиом"
                    else -> "Загрузка данных"
                }
                emit(LoadingStatus.Progress(currentProgress, message))

                // 1. Имитируем запрос в сеть
                delay(1000)

                // 2. Имитируем сохранение в БД
                data.forEach { db.insertDictionaryItem(it) }
                emit(LoadingStatus.Progress(currentProgress, "Сохранение в БД"))
                delay(500)

                // 3. Сохраняем версию в Prefs (имитируем, что с сервера пришла версия "1.0")
                prefs.saveLibVersion(libKey, "1.0")

                // 4. Эмитим прогресс (например, по 25% на каждую либу)

            }
            emit(LoadingStatus.Progress(0.95f, "Загрузка наград"))

            emit(LoadingStatus.Success)
        } catch (_: Exception) {
            emit(LoadingStatus.Error(ErrorType.Unknown))
        }
    }
}