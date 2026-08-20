package com.example.diyca.feature.phrasebooks.screens.phrasebook

sealed class PhrasebookEffect {
    data class NavigateToPhrasebook(val id: String): PhrasebookEffect()
}