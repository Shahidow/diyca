package com.example.speak_caucasus.feature.phrasebooks.screens.phrasebook

import com.example.speak_caucasus.domain.phrasebooks.models.Phrasebook

sealed class PhrasebookMsg {
    object LoadData: PhrasebookMsg()
    data class DataLoaded(
        val phrasebookList: List<Phrasebook>
    ): PhrasebookMsg()
    data class PhrasebookOpen(val id: Int): PhrasebookMsg()
}