package com.example.diyca.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diyca.data.db.dictionary.entity.PhrasebookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhrasebookDao {
    @Query("SELECT * FROM phrasebook_table")
    fun getPhrasebooks(): Flow<List<PhrasebookEntity>>

    @Insert(entity = PhrasebookEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertPhrasebook(phrasebookEntity: PhrasebookEntity)

    @Query("UPDATE phrasebook_table SET image = :localPath WHERE id = :id")
    suspend fun updatePhrasebookImage(id: String, localPath: String)

    @Query("DELETE FROM phrasebook_table")
    suspend fun clearAllPhrasebooks()
}