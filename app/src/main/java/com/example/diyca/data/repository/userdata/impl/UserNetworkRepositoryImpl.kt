package com.example.diyca.data.repository.userdata.impl

import com.example.diyca.data.mappers.UserDataMapper
import com.example.diyca.data.network.UserApi
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.startup.models.RewardsData
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.util.ErrorType
import com.example.diyca.util.Resource
import com.example.diyca.util.handleNetworkError
import com.example.diyca.util.safeApiCall

class UserNetworkRepositoryImpl(
    private val userApi: UserApi,
    private val userDataMapper: UserDataMapper,
    private val userDataBaseRepository: UserDataBaseRepository
) : UserNetworkRepository {

    override suspend fun getProgress(): Resource<List<UserProgress>> = safeApiCall {
        val response = userApi.getProgress()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val progress = userDataMapper.mapProgressDtoToDomain(body)
                Resource.Success(progress)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun setProgress(progressList: List<UserProgress>, newRewardIds: List<String>): Resource<Unit> =
        safeApiCall {
            val request = userDataMapper.mapDomainToSetProgressRequest(progressList, newRewardIds)
            val response = userApi.setProgress(request)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val dailyActivity =
                        userDataMapper.mapDailyActivityDtoToDomain(body) ?: return@let
                    userDataBaseRepository.insertActivity(dailyActivity)
                } ?: Resource.Error(ErrorType.ServerError)
                Resource.Success(Unit)
            } else {
                handleNetworkError(response)
            }
        }

    override suspend fun clearProgress(): Resource<Unit> =
        safeApiCall {
            val response = userApi.clearProgress()
            if (response.isSuccessful) {
                userDataBaseRepository.clearProgress()
                Resource.Success(Unit)
            } else {
                handleNetworkError(response)
            }
        }

    override suspend fun getActivity(): Resource<List<DailyActivity>> = safeApiCall {
        val response = userApi.getActivity()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val activity = userDataMapper.mapActivityDtoToDomain(body)
                Resource.Success(activity)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun getUserRewards(): Resource<List<String>> = safeApiCall {
        val response = userApi.getUserRewards()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                Resource.Success(body.data)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }

    override suspend fun getAllRewards(): Resource<RewardsData> = safeApiCall {
        val response = userApi.getAllRewards()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val domainRewards = userDataMapper.mapRewardListDtoToDomain(body)
                Resource.Success(domainRewards)
            } ?: Resource.Error(ErrorType.ServerError)
        } else {
            handleNetworkError(response)
        }
    }
}