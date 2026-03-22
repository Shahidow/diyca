package com.example.speak_caucasus.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.speak_caucasus.data.db.dictionary.entity.ProverbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProverbDao {
    @Query("SELECT * FROM proverb_table")
    fun getProverbs(): Flow<List<ProverbEntity>>

    @Insert(entity = ProverbEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertProverb(proverbEntity: ProverbEntity)

    @Query("SELECT * FROM proverb_table WHERE id = :id")
    suspend fun getProverbById(id: Int): ProverbEntity

    @Query("SELECT id FROM proverb_table")
    suspend fun getProverbsIds(): List<Int>

    @Update
    suspend fun updateProverb(proverbEntity: ProverbEntity)

    @Query("SELECT * FROM proverb_table WHERE isFavorite = 1")
    fun getFavoritesProverbs(): Flow<List<ProverbEntity>>
}