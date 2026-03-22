package com.example.diyca.feature.phrasebooks.screens.phrasebook

import com.example.diyca.domain.phrasebooks.models.Phrasebook

sealed class PhrasebookMsg {
    object LoadData: PhrasebookMsg()
    data class DataLoaded(
        val phrasebookList: List<Phrasebook>
    ): PhrasebookMsg()
    data class PhrasebookOpen(val id: Int): PhrasebookMsg()
}