package com.example.diyca.data.network

import com.example.diyca.util.Resource
import retrofit2.http.GET

interface DictionaryApi {
    @GET("python/vocabulary")
    suspend fun getVocabulary(): Resource<Unit>
}