package com.example.speak_caucasus.data.db.userdata

import com.example.speak_caucasus.data.db.userdata.entity.ActivityEntity
import com.example.speak_caucasus.data.db.userdata.entity.ProgressEntity
import com.example.speak_caucasus.data.db.userdata.entity.RewardEntity
import com.example.speak_caucasus.domain.home.models.DailyActivity
import com.example.speak_caucasus.domain.home.models.Reward
import com.example.speak_caucasus.domain.learning.models.UserProgress

class UserDataConverter {

    fun mapUserActivity(dailyActivity: DailyActivity): ActivityEntity {
        return ActivityEntity(
            id = dailyActivity.id,
            date = dailyActivity.date,
            lessonsCompleted = dailyActivity.lessonsCompleted,
            tasksCompleted = dailyActivity.tasksCompleted,
        )
    }

    fun mapUserActivity(activityEntity: ActivityEntity): DailyActivity {
        return DailyActivity(
            id = activityEntity.id,
            date = activityEntity.date,
            lessonsCompleted = activityEntity.lessonsCompleted,
            tasksCompleted = activityEntity.tasksCompleted,
        )
    }

    fun mapUserReward(reward: Reward): RewardEntity {
        return RewardEntity(
            rewardId = reward.id,
            imageUrl = reward.imageUrl,
            title = reward.title,
        )
    }

    fun mapUserReward(rewardEntity: RewardEntity): Reward {
        return Reward(
            id = rewardEntity.rewardId,
            imageUrl = rewardEntity.imageUrl,
            title = rewardEntity.title,
        )
    }

    fun mapUserProgress(userProgress: UserProgress): ProgressEntity {
        return ProgressEntity(
            taskId = userProgress.taskId,
            lessonId = userProgress.lessonId,
            themeId = userProgress.themeId
        )
    }

    fun mapUserProgress(progressEntity: ProgressEntity): UserProgress {
        return UserProgress(
            taskId = progressEntity.taskId,
            lessonId = progressEntity.lessonId,
            themeId = progressEntity.themeId
        )
    }
}