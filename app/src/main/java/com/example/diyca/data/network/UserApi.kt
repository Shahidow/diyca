package com.example.diyca.data.network

import com.example.diyca.data.dto.user_data.ActivityResponse
import com.example.diyca.data.dto.user_data.ProgressResponse
import com.example.diyca.data.dto.user_data.RewardsResponse
import com.example.diyca.data.dto.user_data.SetProgressRequest
import com.example.diyca.data.dto.user_data.SetProgressResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("/learn/progress/tree")
    suspend fun getProgress(): Response<ProgressResponse>

    @POST("/learn/progress/solved")
    suspend fun setProgress(@Body request: SetProgressRequest): Response<SetProgressResponse>

    @GET("/learn/activity/calendar")
    suspend fun getActivity(): Response<ActivityResponse>

    @GET("/learn/rewards")
    suspend fun getRewards(): Response<RewardsResponse>
}