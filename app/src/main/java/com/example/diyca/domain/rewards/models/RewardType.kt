package com.example.diyca.domain.rewards.models

enum class RewardType(
    val title: String,
    val category: String,
    val threshold: Int = 0
) {
    TASKS_30("reward_tasks_completed_30", "tasks_completed", 30),
    TASKS_60("reward_tasks_completed_60", "tasks_completed", 60),
    TASKS_120("reward_tasks_completed_120", "tasks_completed", 120),

    STREAK_3("reward_activity_streak_3", "activity_streak", 3),
    STREAK_7("reward_activity_streak_7", "activity_streak", 7),
    STREAK_30("reward_activity_streak_30", "activity_streak", 30),

    TOPICS_2("reward_topics_completed_2", "topics_completed", 2),
    TOPICS_3("reward_topics_completed_3", "topics_completed", 3),
    TOPICS_4("reward_topics_completed_4", "topics_completed", 4),
    TOPICS_5("reward_topics_completed_5", "topics_completed", 5),

    PERFECT_1("reward_perfect_day_1", "perfect_day", 1),
    PERFECT_7("reward_perfect_day_7", "perfect_day", 7),
    PERFECT_30("reward_perfect_day_30", "perfect_day", 30),

    PERFECT_LESSON("reward_lesson_without_mistakes", "perfect_lesson"),

    RETURN_TO_APP("reward_return_to_app", "return_to_app", 7);

    companion object {
        fun fromTitle(title: String): RewardType? = entries.find { it.title == title }
        val activityRewards = entries.filter { it.category == "activity_streak" }
        val topicRewards = entries.filter { it.category == "topics_completed" }
        val tasksRewards = entries.filter { it.category == "tasks_completed" }
        val perfectRewards = entries.filter { it.category == "perfect_day" }
    }
}