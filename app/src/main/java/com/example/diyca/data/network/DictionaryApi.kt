package com.example.diyca.data.network

import com.example.diyca.data.dto.library.PhrasebookResponse
import com.example.diyca.data.dto.library.VersionResponse
import com.example.diyca.data.dto.library.VocabularyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("python/vocabulary")
    suspend fun getVocabulary(): Response<VocabularyResponse>

    @GET("python/version/vocabulary")
    suspend fun getVocabularyVersion(): Response<VersionResponse>

    @GET("python/languages/{language_id}/phrasebook-topics")
    suspend fun getPhrasebooks(@Path("language_id")  languageId: String): Response<PhrasebookResponse>

    @GET("python/version/phrasebook")
    suspend fun getPhrasebookVersion(): Response<VersionResponse>
}