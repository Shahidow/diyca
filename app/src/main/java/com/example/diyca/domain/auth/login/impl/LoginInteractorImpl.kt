package com.example.diyca.domain.auth.login.impl

import com.example.diyca.data.repository.auth.AuthRepository
import com.example.diyca.data.repository.userdata.UserDataBaseRepository
import com.example.diyca.data.repository.userdata.UserNetworkRepository
import com.example.diyca.domain.auth.login.LoginInteractor
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.domain.auth.models.UserData
import com.example.diyca.domain.session.SessionManager
import com.example.diyca.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoginInteractorImpl(
    private val authRepository: AuthRepository,
    private val userNetworkRepository: UserNetworkRepository,
    private val userDataBaseRepository: UserDataBaseRepository,
    private val sessionManager: SessionManager
) : LoginInteractor {
    override suspend fun login(loginData: LoginData): Resource<UserData> {
        userDataBaseRepository.clearAllData()
        val loginResource = authRepository.login(loginData)
        if (loginResource is Resource.Error) return loginResource
        if (loginResource is Resource.Success) {
            val userData = loginResource.data
            if (userData != null) {
                sessionManager.saveTokens(
                    access = userData.accessToken,
                    refresh = userData.refreshToken
                )
            }
        }

        return coroutineScope {
            val progressDeferred = async { userNetworkRepository.getProgress() }
            val activityDeferred = async { userNetworkRepository.getActivity() }
            val rewardsDeferred = async { userNetworkRepository.getUserRewards() }


            val progressRes = progressDeferred.await()
            val activityRes = activityDeferred.await()
            val rewardsRes = rewardsDeferred.await()


            when {
                progressRes is Resource.Error -> {
                    sessionManager.logout()
                    Resource.Error(progressRes.errorType, progressRes.resultCode)
                }

                activityRes is Resource.Error -> {
                    sessionManager.logout()
                    Resource.Error(activityRes.errorType, activityRes.resultCode)
                }

                rewardsRes is Resource.Error -> {
                    sessionManager.logout()
                    Resource.Error(rewardsRes.errorType, rewardsRes.resultCode)
                }

                else -> {
                    val progressData = (progressRes as Resource.Success).data ?: emptyList()
                    progressData.forEach { progress ->
                        userDataBaseRepository.insertUserProgress(
                            progress
                        )
                    }
                    val activityData = (activityRes as Resource.Success).data ?: emptyList()
                    activityData.forEach { activity ->
                        userDataBaseRepository.insertActivity(
                            activity
                        )
                    }
                    val rewardsData = (rewardsRes as Resource.Success).data?: emptyList()
                    userDataBaseRepository.insertUserRewards(rewardsData)

                    sessionManager.markAuthorized()
                    loginResource
                }
            }
        }
    }
}