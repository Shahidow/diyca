package com.example.diyca.domain.rewards

import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.learning.models.UserProgress
import com.example.diyca.domain.rewards.models.LessonResult
import com.example.diyca.domain.rewards.models.RewardCalculationInput
import com.example.diyca.util.DAILY_LESSONS_GOAL
import com.example.diyca.util.DAILY_TASKS_GOAL
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class RewardEvaluator {
    fun evaluate(input: RewardCalculationInput): List<String> {
        val newRewards = mutableListOf<String>()
        val (history, progress, earned, current, allAvailableRewards) = input

        // 1. perfect_lesson (урок без ошибок)
        val perfectLessonReward = allAvailableRewards.find { it.category == "perfect_lesson" }
        if (perfectLessonReward != null && perfectLessonReward.title !in earned) {
            if (current.lessonTasksCount == current.completedTasks.size) {
                newRewards.add(perfectLessonReward.title)
            }
        }

        // 2. activity_streak (дни подряд)
        val streak = calculateStreak(history, current.date)
        allAvailableRewards.filter { it.category == "activity_streak" }.forEach { reward ->
            if (streak >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 3. perfect_day (выполнение дневной нормы)
        val perfectDaysCount = calculatePerfectDaysCount(history, current)
        allAvailableRewards.filter { it.category == "perfect_day" }.forEach { reward ->
            if (perfectDaysCount >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 4. tasks_completed (всего выполненных заданий)
        val totalTasks = history.sumOf { it.tasksCompleted } + current.completedTasks.size
        allAvailableRewards.filter { it.category == "tasks_completed" }.forEach { reward ->
            if (totalTasks >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 5. topics_completed (полностью пройденные темы)
        val completedTopicsCount = calculateCompletedTopicsCount(progress, current)
        allAvailableRewards.filter { it.category == "topics_completed" }.forEach { reward ->
            if (completedTopicsCount >= reward.threshold && reward.title !in earned) {
                newRewards.add(reward.title)
            }
        }

        // 6. return_to_app (возврат в приложение после перерыва)
        val returnReward = allAvailableRewards.find { it.category == "return_to_app" }
        if (returnReward != null && returnReward.title !in earned) {
            val gap = calculateGap(history, current.date)
            if (gap >= returnReward.threshold) {
                newRewards.add(returnReward.title)
            }
        }

        return newRewards
    }

    private fun calculateStreak(history: List<DailyActivity>, todayStr: String): Int {
        val dates = (history.map { it.date } + todayStr)
            .distinct()
            .map { LocalDate.parse(it) }
            .sortedDescending()

        if (dates.isEmpty() || dates.first() != LocalDate.parse(todayStr)) return 0

        var streak = 1
        for (i in 0 until dates.size - 1) {
            if (ChronoUnit.DAYS.between(dates[i + 1], dates[i]) == 1L) {
                streak++
            } else break
        }
        return streak
    }

    private fun calculatePerfectDaysCount(
        history: List<DailyActivity>,
        current: LessonResult
    ): Int {
        val perfectDates = history.filter {
            it.lessonsCompleted >= DAILY_LESSONS_GOAL || it.tasksCompleted >= DAILY_TASKS_GOAL
        }.map { it.date }.toMutableSet()

        val todayInHistory = history.find { it.date == current.date }
        val totalTodayLessons = (todayInHistory?.lessonsCompleted ?: 0) + (if (current.isLessonCompleted) 1 else 0)
        val totalTodayTasks = (todayInHistory?.tasksCompleted ?: 0) + current.completedTasks.size

        if (totalTodayLessons >= DAILY_LESSONS_GOAL || totalTodayTasks >= DAILY_TASKS_GOAL) {
            perfectDates.add(current.date)
        }
        return perfectDates.size
    }

    private fun calculateCompletedTopicsCount(
        progress: List<UserProgress>,
        current: LessonResult
    ): Int {
        val completedTopicIds = progress.map { it.topicId }.filter { it != current.topicId }.toMutableSet()
        if (current.isTopicCompleted) {
            completedTopicIds.add(current.topicId)
        }
        return completedTopicIds.size
    }

    private fun calculateGap(history: List<DailyActivity>, todayStr: String): Long {
        val lastActivityDateStr = history.map { it.date }
            .filter { it != todayStr }
            .maxOrNull() ?: return 0

        return try {
            val lastDate = LocalDate.parse(lastActivityDateStr)
            val currentDate = LocalDate.parse(todayStr)
            ChronoUnit.DAYS.between(lastDate, currentDate)
        } catch (_: Exception) {
            0
        }
    }
}