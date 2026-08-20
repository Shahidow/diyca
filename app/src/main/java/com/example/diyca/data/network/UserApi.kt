package com.example.diyca.data.network

import com.example.diyca.data.dto.user_data.ActivityResponse
import com.example.diyca.data.dto.user_data.GetAllRewardsResponse
import com.example.diyca.data.dto.user_data.ProgressResponse
import com.example.diyca.data.dto.user_data.SetProgressRequest
import com.example.diyca.data.dto.user_data.SetProgressResponse
import com.example.diyca.data.dto.user_data.UserRewardsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("/learn/progress/tree")
    suspend fun getProgress(): Response<ProgressResponse>

    @POST("/learn/progress/solved")
    suspend fun setProgress(@Body request: SetProgressRequest): Response<SetProgressResponse>

    @GET("/learn/progress/clear")
    suspend fun clearProgress(): Response<Unit>

    @GET("/learn/activity/calendar")
    suspend fun getActivity(): Response<ActivityResponse>

    @GET("/learn/rewards")
    suspend fun getUserRewards(): Response<UserRewardsResponse>

    @GET("/python/reward")
    suspend fun getAllRewards(): Response<GetAllRewardsResponse>
}