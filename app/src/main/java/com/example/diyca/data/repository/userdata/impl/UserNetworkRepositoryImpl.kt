package com.example.diyca.data.repository.userdata.impl

import com.example.diyca.data.mappers.UserDataMapper
import com.example.diyca.data.network.UserApi
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.home.models.Reward
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

    override suspend fun setProgress(progressList: List<UserProgress>): Resource<Unit> =
        safeApiCall {
            val request = userDataMapper.mapDomainToSetProgressRequest(progressList)
            val response = userApi.setProgress(request)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val dailyActivity =
                        userDataMapper.mapDailyActivityDtoToDomain(body) ?: return@let
                    userDataBaseRepository.insertActivity(dailyActivity)
                }
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

    override suspend fun getRewards(): Resource<List<Reward>> {
        TODO("Not yet implemented")
    }
}