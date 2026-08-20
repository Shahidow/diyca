package com.example.diyca.data.mappers

import com.example.diyca.data.dto.library.PhrasebookResponse
import com.example.diyca.data.dto.library.VocabularyResponse
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import com.example.diyca.domain.startup.models.PhrasebookData
import com.example.diyca.domain.startup.models.VocabularyData

class DictionaryResponseMapper {

    fun phrasebookResponseMapper(
        dto: PhrasebookResponse,
        favoriteIds: Set<String> = emptySet()
    ): PhrasebookData {
        val phrasebooks = mutableListOf<Phrasebook>()
        val phrasebookItems = mutableListOf<DictionaryItem.PhrasebookItem>()

        dto.data.forEach { topicData ->
            phrasebooks.add(
                Phrasebook(
                    id = topicData.topic.id,
                    title = topicData.topic.name,
                    image = null
                )
            )

            topicData.phrases.forEach { phraseDto ->
                phrasebookItems.add(
                    DictionaryItem.PhrasebookItem(
                        parentId = topicData.topic.id,
                        usingExample = phraseDto.usingExample?.joinToString("\n") ?: "",
                        id = phraseDto.id,
                        original = phraseDto.text,
                        translation = phraseDto.translation.joinToString(", "),
                        isFavorite = phraseDto.id in favoriteIds,
                        audio = phraseDto.audio
                    )
                )
            }
        }

        return PhrasebookData(
            version = dto.version,
            phrasebookList = phrasebooks,
            phrasebookItems = phrasebookItems
        )
    }

    fun vocabularyResponseMapper(
        dto: VocabularyResponse,
        favoriteIds: Set<String> = emptySet()
    ): VocabularyData {
        return VocabularyData(
            version = dto.version,
            words = dto.data.map { itemDto ->
                DictionaryItem.Word(
                    id = itemDto.id,
                    original = itemDto.word,
                    translation = itemDto.translation.joinToString(", "),
                    isFavorite = itemDto.id in favoriteIds,
                    audio = itemDto.audio
                )
            }
        )
    }
}