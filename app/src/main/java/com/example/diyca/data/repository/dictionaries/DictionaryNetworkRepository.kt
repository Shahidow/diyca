package com.example.diyca.data.repository.dictionaries

import com.example.diyca.util.Resource

interface DictionaryNetworkRepository {
    suspend fun getVocabulary(): Resource<Unit>
}