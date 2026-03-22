package com.example.speak_caucasus.feature.learning.screens.lesson


sealed class LessonEffect {
    object NavigateBack: LessonEffect()
    data class NavigateToSection(val sectionId: String): LessonEffect()
}