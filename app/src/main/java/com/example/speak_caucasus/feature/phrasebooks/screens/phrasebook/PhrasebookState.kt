package com.example.speak_caucasus.feature.phrasebooks.screens.phrasebook

import com.example.speak_caucasus.domain.phrasebooks.models.Phrasebook

data class PhrasebookState (
    val isLoading: Boolean = false,
    val phrasebookList: List<Phrasebook> = emptyList()
)