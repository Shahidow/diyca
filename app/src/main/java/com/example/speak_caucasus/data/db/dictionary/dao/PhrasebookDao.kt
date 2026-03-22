package com.example.speak_caucasus.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.speak_caucasus.data.db.dictionary.entity.PhrasebookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhrasebookDao {
    @Query("SELECT * FROM phrasebook_table")
    fun getPhrasebooks(): Flow<List<PhrasebookEntity>>

    @Insert(entity = PhrasebookEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertPhrasebook(phrasebookEntity: PhrasebookEntity)

    @Update
    suspend fun updatePhrasebook(phrasebookEntity: PhrasebookEntity)
}