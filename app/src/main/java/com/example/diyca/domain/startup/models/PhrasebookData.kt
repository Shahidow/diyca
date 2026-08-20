package com.example.diyca.domain.startup.models

import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.phrasebooks.models.Phrasebook

data class PhrasebookData(
    val version: Int,
    val phrasebookList: List<Phrasebook>,
    val phrasebookItems: List<DictionaryItem.PhrasebookItem>
)
