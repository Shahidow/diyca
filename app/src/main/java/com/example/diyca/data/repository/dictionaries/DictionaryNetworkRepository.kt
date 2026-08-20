package com.example.diyca.data.repository.dictionaries

import com.example.diyca.domain.startup.models.PhrasebookData
import com.example.diyca.domain.startup.models.VocabularyData
import com.example.diyca.util.Resource

interface DictionaryNetworkRepository {
    suspend fun getVocabulary(): Resource<VocabularyData>
    suspend fun getPhrasebooks(languageId: String): Resource<PhrasebookData>
    suspend fun getVocabularyVersion(): Resource<Int>
    suspend fun getPhrasebookVersion(): Resource<Int>
}