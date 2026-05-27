package com.example.diyca.data.repository.dictionaries.impl

import com.example.diyca.data.network.DictionaryApi
import com.example.diyca.data.repository.dictionaries.DictionaryNetworkRepository
import com.example.diyca.util.Resource

class DictionaryNetworkRepositoryImpl(
    private val dictionaryApi: DictionaryApi
): DictionaryNetworkRepository {
    override suspend fun getVocabulary(): Resource<Unit> {
        TODO("Not yet implemented")
    }
}