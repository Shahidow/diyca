package com.example.diyca.data.mappers

import com.example.diyca.data.dto.user_data.ActivityResponse
import com.example.diyca.data.dto.user_data.ProgressResponse
import com.example.diyca.data.dto.user_data.RewardResponse
import com.example.diyca.data.dto.user_data.SetProgressChapterDto
import com.example.diyca.data.dto.user_data.SetProgressDataDto
import com.example.diyca.data.dto.user_data.SetProgressRequest
import com.example.diyca.data.dto.user_data.SetProgressResponse
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.learning.models.UserProgress
import java.util.TimeZone

class UserDataMapper {
    fun mapProgressDtoToDomain(dto: ProgressResponse): List<UserProgress> {
        return dto.data?.chapters?.flatMap { chapterDto ->
            chapterDto.tasks.map { taskId ->
                UserProgress(
                    taskId = taskId,
                    lessonId = chapterDto.lesson,
                    topicId = chapterDto.topic
                )
            }
        } ?: emptyList()
    }

    fun mapDomainToSetProgressRequest(progressList: List<UserProgress>): SetProgressRequest {
        val chapters = progressList
            .groupBy { it.topicId to it.lessonId }
            .map { (pair, tasks) ->
                SetProgressChapterDto(
                    topic = pair.first,
                    lesson = pair.second,
                    tasks = tasks.map { it.taskId }
                )
            }
        val currentTimeZone = TimeZone.getDefault().id
        return SetProgressRequest(
            timezone = currentTimeZone,
            progress = SetProgressDataDto(
                chapters = chapters
            )
        )
    }

    fun mapActivityDtoToDomain(dto: ActivityResponse): List<DailyActivity> {
        return dto.data?.map { itemDto ->
            DailyActivity(
                date = itemDto.dateKey,
                lessonsCompleted = itemDto.lessonsCompleted,
                tasksCompleted = itemDto.tasksCompleted
            )
        } ?: emptyList()
    }

    fun mapDailyActivityDtoToDomain(dto: SetProgressResponse): DailyActivity? {
        val activityDto = dto.data?.activity ?: return null
        return DailyActivity(
            date = activityDto.dateKey,
            lessonsCompleted = activityDto.lessonsCompleted,
            tasksCompleted = activityDto.tasksCompleted
        )
    }

    fun mapRewardsDtoToDomain(dto: List<RewardResponse>): List<Reward> {
        return dto.map { itemDto->
            Reward(
                id = itemDto.id,
                title = itemDto.rewardTitle,
                category = itemDto.category,
                name = itemDto.rewardName,
                imageUrl = itemDto.imageUrl
            )
        }
    }
}