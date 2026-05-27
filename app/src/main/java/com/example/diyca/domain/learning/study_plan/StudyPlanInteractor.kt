package com.example.diyca.domain.learning.study_plan

import com.example.diyca.domain.learning.models.Topic
import com.example.diyca.util.Resource
import kotlinx.coroutines.flow.Flow

interface StudyPlanInteractor {
    fun getTopics(): Flow<Resource<List<Topic>>>
}