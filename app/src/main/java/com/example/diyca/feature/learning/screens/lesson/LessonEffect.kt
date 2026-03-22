package com.example.diyca.feature.learning.screens.lesson


sealed class LessonEffect {
    object NavigateBack: LessonEffect()
    data class NavigateToSection(val sectionId: String): LessonEffect()
}