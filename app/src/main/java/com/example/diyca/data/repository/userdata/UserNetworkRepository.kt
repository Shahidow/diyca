package com.example.diyca.data.repository.userdata

import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.startup.models.RewardsData
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.learning.tasks_result.models.TasksResultModel
import com.example.diyca.util.Resource

interface UserNetworkRepository {
    suspend fun getProgress(): Resource<List<UserProgress>>
    suspend fun setProgress(progressList: List<UserProgress>, newRewardIds: List<String>): Resource<TasksResultModel>
    suspend fun clearProgress(): Resource<Unit>
    suspend fun getActivity(): Resource<List<DailyActivity>>
    suspend fun getUserRewards(): Resource<List<String>>
    suspend fun getAllRewards(): Resource<RewardsData>
}