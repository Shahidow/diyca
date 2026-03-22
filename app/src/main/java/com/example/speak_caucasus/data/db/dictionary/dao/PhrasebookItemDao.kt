package com.example.speak_caucasus.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.speak_caucasus.data.db.dictionary.entity.PhrasebookItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhrasebookItemDao {
    @Query("SELECT * FROM phrasebook_item_table")
    fun getPhrasebookItems(): Flow<List<PhrasebookItemEntity>>

    @Insert(entity = PhrasebookItemEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertPhrasebookItem(conversationItemEntity: PhrasebookItemEntity)

    @Query("SELECT * FROM phrasebook_item_table WHERE id = :id")
    suspend fun getPhrasebookItemById(id: Int): PhrasebookItemEntity

    @Query("SELECT id FROM phrasebook_item_table")
    suspend fun getFavoritesPhrasebookItemsIds(): List<Int>

    @Update
    suspend fun updatePhrasebookItem(phrasebookItemEntity: PhrasebookItemEntity)

    @Query("SELECT * FROM phrasebook_item_table WHERE isFavorite = 1")
    fun getFavoritesPhrasebookItems(): Flow<List<PhrasebookItemEntity>>

    @Query("SELECT * FROM phrasebook_item_table WHERE parentId = :parentId")
    fun getPhrasebookByTopic(parentId: Int): Flow<List<PhrasebookItemEntity>>
}