package com.example.diyca.data.repository.dictionaries

import com.example.diyca.data.db.dictionary.DictionaryDatabase
import com.example.diyca.data.db.dictionary.DictionaryItemConverter
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DictionaryRepositoryImpl(
    private val dictionaryDatabase: DictionaryDatabase,
    private val dictionaryItemConverter: DictionaryItemConverter
) : DictionaryRepository {
    override fun getDictionary(type: DictionaryType): Flow<List<DictionaryItem>> =
        when (type) {
            DictionaryType.EXPRESSION -> dictionaryDatabase.expressionDao().getExpressions()
                .map { list -> list.map { dictionaryItemConverter.mapExpression(it) } }
                .catch { emit(emptyList()) }

            DictionaryType.PHRASEBOOK -> dictionaryDatabase.phrasebookItemDao().getPhrasebookItems()
                .map { list -> list.map { dictionaryItemConverter.mapPhrasebookItem(it) } }
                .catch { emit(emptyList()) }

            DictionaryType.PROVERB -> dictionaryDatabase.proverbDao().getProverbs()
                .map { list -> list.map { dictionaryItemConverter.mapProverb(it) } }
                .catch { emit(emptyList()) }

            DictionaryType.WORD -> dictionaryDatabase.wordDao().getWords()
                .map { list -> list.map { dictionaryItemConverter.mapWord(it) } }
                .catch { emit(emptyList()) }
        }

    override suspend fun insertDictionaryItem(dictionaryItem: DictionaryItem) {

        when (dictionaryItem) {
            is DictionaryItem.Expression -> dictionaryDatabase.expressionDao()
                .insertExpression(dictionaryItemConverter.mapExpression(dictionaryItem))

            is DictionaryItem.PhrasebookItem -> dictionaryDatabase.phrasebookItemDao()
                .insertPhrasebookItem(dictionaryItemConverter.mapPhrasebookItem(dictionaryItem))

            is DictionaryItem.Proverb -> dictionaryDatabase.proverbDao()
                .insertProverb(dictionaryItemConverter.mapProverb(dictionaryItem))

            is DictionaryItem.Word -> dictionaryDatabase.wordDao()
                .insertWord(dictionaryItemConverter.mapWord(dictionaryItem))
        }
    }

    override fun getPhrasebooks(): Flow<List<Phrasebook>> =
        dictionaryDatabase.phrasebookDao().getPhrasebooks()
            .map { list -> list.map { dictionaryItemConverter.mapPhrasebook(it) } }
            .catch { emit(emptyList()) }


    override suspend fun insertPhrasebook(phrasebook: Phrasebook) {
        dictionaryDatabase.phrasebookDao()
            .insertPhrasebook(dictionaryItemConverter.mapPhrasebook(phrasebook))
    }
}