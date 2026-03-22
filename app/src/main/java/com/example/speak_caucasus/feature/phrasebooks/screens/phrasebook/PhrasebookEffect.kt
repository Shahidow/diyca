package com.example.speak_caucasus.feature.phrasebooks.screens.phrasebook

sealed class PhrasebookEffect {
    data class NavigateToPhrasebook(val id: Int): PhrasebookEffect()
}