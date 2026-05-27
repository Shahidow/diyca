package com.example.diyca.feature.phrasebooks.screens.phrasebook

import com.example.diyca.domain.phrasebooks.models.Phrasebook

data class PhrasebookState (
    val phrasebookList: List<Phrasebook> = emptyList()
)