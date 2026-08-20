package com.example.diyca.data.db.userdata

import com.example.diyca.data.db.userdata.entity.ActivityEntity
import com.example.diyca.data.db.userdata.entity.ProgressEntity
import com.example.diyca.data.db.userdata.entity.RewardEntity
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.learning.models.UserProgress

class UserDataConverter {

    fun mapUserActivity(dailyActivity: DailyActivity): ActivityEntity {
        return ActivityEntity(
            date = dailyActivity.date,
            lessonsCompleted = dailyActivity.lessonsCompleted,
            tasksCompleted = dailyActivity.tasksCompleted,
        )
    }

    fun mapUserActivity(activityEntity: ActivityEntity): DailyActivity {
        return DailyActivity(
            date = activityEntity.date,
            lessonsCompleted = activityEntity.lessonsCompleted,
            tasksCompleted = activityEntity.tasksCompleted,
        )
    }

    fun mapUserReward(reward: Reward): RewardEntity {
        return RewardEntity(
            rewardId = reward.id,
            imageUrl = reward.image,
            title = reward.title,
            name = reward.name,
            category = reward.category,
        )
    }

    fun mapUserReward(rewardEntity: RewardEntity): Reward {
        return Reward(
            id = rewardEntity.rewardId,
            image = rewardEntity.imageUrl,
            title = rewardEntity.title,
            category = rewardEntity.category,
            name = rewardEntity.name,
        )
    }

    fun mapUserProgress(userProgress: UserProgress): ProgressEntity {
        return ProgressEntity(
            taskId = userProgress.taskId,
            lessonId = userProgress.lessonId,
            themeId = userProgress.topicId
        )
    }

    fun mapUserProgress(progressEntity: ProgressEntity): UserProgress {
        return UserProgress(
            taskId = progressEntity.taskId,
            lessonId = progressEntity.lessonId,
            topicId = progressEntity.themeId
        )
    }
}