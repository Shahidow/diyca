package com.example.diyca.data.repository.dictionaries.impl

import com.example.diyca.data.mappers.DictionaryResponseMapper
import com.example.diyca.data.network.DictionaryApi
import com.example.diyca.data.repository.dictionaries.DictionaryDataBaseRepository
import com.example.diyca.data.repository.dictionaries.DictionaryNetworkRepository
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.domain.startup.models.PhrasebookData
import com.example.diyca.domain.startup.models.VocabularyData
import com.example.diyca.util.ErrorType
import com.example.diyca.util.Resource
import com.example.diyca.util.handleNetworkError
import com.example.diyca.util.safeApiCall

class DictionaryNetworkRepositoryImpl(
    private val dictionaryApi: DictionaryApi,
    private val dictionaryDBRepository: DictionaryDataBaseRepository,
    private val dictionaryResponseMapper: DictionaryResponseMapper
) : DictionaryNetworkRepository {
    override suspend fun getVocabulary(): Resource<VocabularyData> = safeApiCall {
        val response = dictionaryApi.getVocabulary()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val favoriteIds = dictionaryDBRepository.getDictionaryFavoriteIds(DictionaryType.WORD).toSet()
                val vocabularyData = dictionaryResponseMapper.vocabularyResponseMapper(body, favoriteIds)
                Resource.Success(vocabularyData)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun getPhrasebooks(languageId: String): Resource<PhrasebookData> =
        safeApiCall {
            val response = dictionaryApi.getPhrasebooks(languageId)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val favoriteIds = dictionaryDBRepository.getDictionaryFavoriteIds(DictionaryType.PHRASEBOOK).toSet()
                    val phrasebookData = dictionaryResponseMapper.phrasebookResponseMapper(body, favoriteIds)
                    Resource.Success(phrasebookData)
                } ?: Resource.Error(ErrorType.ServerError)
            } else {
                handleNetworkError(response)
            }
        }

    override suspend fun getVocabularyVersion(): Resource<Int> = safeApiCall {
        val response = dictionaryApi.getVocabularyVersion()
        if(response.isSuccessful){
            response.body()?.let { body->
                Resource.Success(body.version)
            }?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun getPhrasebookVersion(): Resource<Int> = safeApiCall {
        val response = dictionaryApi.getPhrasebookVersion()
        if(response.isSuccessful){
            response.body()?.let { body->
                Resource.Success(body.version)
            }?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }
}