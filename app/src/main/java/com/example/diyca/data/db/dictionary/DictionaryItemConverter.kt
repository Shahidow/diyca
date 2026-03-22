package com.example.diyca.data.db.dictionary


import com.example.diyca.data.db.dictionary.entity.ExpressionEntity
import com.example.diyca.data.db.dictionary.entity.PhrasebookEntity
import com.example.diyca.data.db.dictionary.entity.PhrasebookItemEntity
import com.example.diyca.data.db.dictionary.entity.ProverbEntity
import com.example.diyca.data.db.dictionary.entity.WordEntity
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.phrasebooks.models.Phrasebook

class DictionaryItemConverter {

    fun mapWord(word: DictionaryItem.Word): WordEntity {
        return WordEntity(
            id = word.id,
            original = word.original,
            translation = word.translation,
            isFavorite = word.isFavorite,
            audio = word.audio
        )
    }

    fun mapWord(word: WordEntity): DictionaryItem.Word {
        return DictionaryItem.Word(
            id = word.id,
            original = word.original,
            translation = word.translation,
            isFavorite = word.isFavorite,
            audio = word.audio
        )
    }

    fun mapProverb(proverb: DictionaryItem.Proverb): ProverbEntity {
        return ProverbEntity(
            id = proverb.id,
            original = proverb.original,
            translation = proverb.translation,
            isFavorite = proverb.isFavorite,
            audio = proverb.audio
        )
    }

    fun mapProverb(proverb: ProverbEntity): DictionaryItem.Proverb {
        return DictionaryItem.Proverb(
            id = proverb.id,
            original = proverb.original,
            translation = proverb.translation,
            isFavorite = proverb.isFavorite,
            audio = proverb.audio
        )
    }

    fun mapExpression(expression: DictionaryItem.Expression): ExpressionEntity {
        return ExpressionEntity(
            id = expression.id,
            original = expression.original,
            translation = expression.translation,
            isFavorite = expression.isFavorite,
            audio = expression.audio
        )
    }

    fun mapExpression(expression: ExpressionEntity): DictionaryItem.Expression {
        return DictionaryItem.Expression(
            id = expression.id,
            original = expression.original,
            translation = expression.translation,
            isFavorite = expression.isFavorite,
            audio = expression.audio
        )
    }

    fun mapPhrasebookItem(phrasebookItem: DictionaryItem.PhrasebookItem): PhrasebookItemEntity {
        return PhrasebookItemEntity(
            id = phrasebookItem.id,
            parentId = phrasebookItem.parentId,
            original = phrasebookItem.original,
            translation = phrasebookItem.translation,
            isFavorite = phrasebookItem.isFavorite,
            audio = phrasebookItem.audio
        )
    }

    fun mapPhrasebookItem(phrasebookItem: PhrasebookItemEntity): DictionaryItem.PhrasebookItem {
        return DictionaryItem.PhrasebookItem(
            id = phrasebookItem.id,
            parentId = phrasebookItem.parentId,
            original = phrasebookItem.original,
            translation = phrasebookItem.translation,
            isFavorite = phrasebookItem.isFavorite,
            audio = phrasebookItem.audio
        )
    }

    fun mapPhrasebook(phrasebook: Phrasebook): PhrasebookEntity {
        return PhrasebookEntity(
            id = phrasebook.id,
            title = phrasebook.title,
            pic = phrasebook.pic,
        )
    }

    fun mapPhrasebook(phrasebook: PhrasebookEntity): Phrasebook {
        return Phrasebook(
            id = phrasebook.id,
            title = phrasebook.title,
            pic = phrasebook.pic,
        )
    }
}