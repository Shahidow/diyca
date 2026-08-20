package com.example.diyca.domain.rewards

import com.example.diyca.domain.rewards.models.RewardCalculationInput
import com.example.diyca.domain.rewards.models.RewardType
import com.example.diyca.util.DAILY_LESSONS_GOAL
import com.example.diyca.util.DAILY_TASKS_GOAL
import com.example.diyca.util.DATE_FORMAT
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class RewardEvaluator {
    fun evaluate(input: RewardCalculationInput): List<String> {
        val newRewards = mutableListOf<String>()
        val (history, progress, earned, current) = input

        // 1. perfect_lesson
        if (current.lessonTasksCount == current.completedTasks.size) {
            val title = RewardType.PERFECT_LESSON.title
            if (title !in earned) newRewards.add(title)
        }

        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT) //todo
        val uniqueDates = history.map { it.date }.toMutableSet()
        uniqueDates.add(current.date)
        val sortedDates = uniqueDates
            .map { LocalDate.parse(it, formatter) }
            .sortedDescending()

        // 2. activity_streak
        var streak = 0
        if (sortedDates.isNotEmpty()) {
            val today = LocalDate.parse(current.date, formatter)
            if (sortedDates.first() == today) {
                streak = 1
                for (i in 0 until sortedDates.size - 1) {
                    if (ChronoUnit.DAYS.between(sortedDates[i + 1], sortedDates[i]) == 1L) {
                        streak++
                    } else {
                        break
                    }
                }
            }
        }
        RewardType.activityRewards.forEach { reward ->
            if (streak >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 3. perfect_day
        val perfectDates = history.filter {
            it.lessonsCompleted >= DAILY_LESSONS_GOAL || it.tasksCompleted >= DAILY_TASKS_GOAL
        }.map { it.date }.toMutableSet()
        val todayInHistory = history.find { it.date == current.date }
        val totalTodayLessons =
            (todayInHistory?.lessonsCompleted ?: 0) + (if (current.isLessonCompleted) 1 else 0)
        val totalTodayTasks = (todayInHistory?.tasksCompleted ?: 0) + current.completedTasks.size
        if (totalTodayLessons >= DAILY_LESSONS_GOAL || totalTodayTasks >= DAILY_TASKS_GOAL) {
            perfectDates.add(current.date)
        }
        val perfectDaysCount = perfectDates.size
        RewardType.perfectRewards.forEach { reward ->
            if (perfectDaysCount >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 4. tasks_completed
        val totalTasks = history.sumOf { it.tasksCompleted } + current.completedTasks.size
        RewardType.tasksRewards.forEach { reward ->
            if (totalTasks >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 5. topics_completed
        val completedTopicIds =
            progress.map { it.topicId }.filter { it != current.topicId }.toMutableSet()
        if (current.isTopicCompleted) {
            completedTopicIds.add(current.topicId)
        }
        val completedTopicsCount = completedTopicIds.size
        RewardType.topicRewards.forEach { reward ->
            if (completedTopicsCount >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 6. return_to_app
        val lastActivityDateStr = history.map { it.date }
            .filter { it != current.date }
            .maxOrNull()

        if (lastActivityDateStr != null) {
            try {
                val lastDate = LocalDate.parse(lastActivityDateStr, formatter)
                val currentDate = LocalDate.parse(current.date, formatter)
                val gap = ChronoUnit.DAYS.between(lastDate, currentDate)
                if (gap >= RewardType.RETURN_TO_APP.threshold && RewardType.RETURN_TO_APP.title !in earned) {
                    newRewards.add(RewardType.RETURN_TO_APP.title)
                }
            } catch (_: Exception) {
            }
        }

        return newRewards
    }
}