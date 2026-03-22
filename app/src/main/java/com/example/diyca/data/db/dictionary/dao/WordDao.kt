package com.example.diyca.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diyca.data.db.dictionary.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM word_table")
    fun getWords(): Flow<List<WordEntity>>

    @Insert(entity = WordEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertWord(wordEntity: WordEntity)

    @Query("SELECT * FROM word_table WHERE id = :id")
    suspend fun getWordById(id: Int): WordEntity

    @Query("SELECT id FROM word_table")
    suspend fun getWordsIds(): List<Int>

    @Update
    suspend fun updateWord(wordEntity: WordEntity)

    @Query("SELECT * FROM word_table WHERE isFavorite = 1")
    fun getFavoritesWords(): Flow<List<WordEntity>>
}