package com.example.diyca.data.repository.favorites

import com.example.diyca.data.db.dictionary.DictionaryDatabase
import com.example.diyca.data.db.dictionary.DictionaryItemConverter
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dictionaryDatabase: DictionaryDatabase,
    private val dictionaryItemConverter: DictionaryItemConverter
) : FavoritesRepository {
    override fun getAllFavorites(type: DictionaryType): Flow<List<DictionaryItem>> =
        when (type) {
            DictionaryType.EXPRESSION -> dictionaryDatabase.expressionDao()
                .getFavoritesExpressions()
                .map { list -> list.map { dictionaryItemConverter.mapExpression(it) } }
                .catch { emit(emptyList()) }

            DictionaryType.PHRASEBOOK -> dictionaryDatabase.phrasebookItemDao()
                .getFavoritesPhrasebookItems()
                .map { list -> list.map { dictionaryItemConverter.mapPhrasebookItem(it) } }
                .catch { emit(emptyList()) }

            DictionaryType.PROVERB -> dictionaryDatabase.proverbDao()
                .getFavoritesProverbs()
                .map { list -> list.map { dictionaryItemConverter.mapProverb(it) } }
                .catch { emit(emptyList()) }

            DictionaryType.WORD -> dictionaryDatabase.wordDao()
                .getFavoritesWords()
                .map { list -> list.map { dictionaryItemConverter.mapWord(it) } }
                .catch { emit(emptyList()) }
        }

    override suspend fun updateFavoriteItem(item: DictionaryItem) {
        when (item) {
            is DictionaryItem.PhrasebookItem -> {
                val updatedItem = item.copy(isFavorite = !item.isFavorite)
                val conversationItemEntity = dictionaryItemConverter.mapPhrasebookItem(updatedItem)
                dictionaryDatabase.phrasebookItemDao()
                    .updatePhrasebookItem(conversationItemEntity)
            }

            is DictionaryItem.Expression -> {
                val updatedItem = item.copy(isFavorite = !item.isFavorite)
                val expressionEntity = dictionaryItemConverter.mapExpression(updatedItem)
                dictionaryDatabase.expressionDao()
                    .updateExpression(expressionEntity)
            }

            is DictionaryItem.Proverb -> {
                val updatedItem = item.copy(isFavorite = !item.isFavorite)
                val proverbEntity = dictionaryItemConverter.mapProverb(updatedItem)
                dictionaryDatabase.proverbDao().updateProverb(proverbEntity)
            }

            is DictionaryItem.Word -> {
                val updatedItem = item.copy(isFavorite = !item.isFavorite)
                val wordEntity = dictionaryItemConverter.mapWord(updatedItem)
                dictionaryDatabase.wordDao().updateWord(wordEntity)
            }
        }
    }

}