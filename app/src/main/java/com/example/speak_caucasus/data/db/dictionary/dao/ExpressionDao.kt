package com.example.speak_caucasus.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.speak_caucasus.data.db.dictionary.entity.ExpressionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpressionDao {
    @Query("SELECT * FROM expression_table")
    fun getExpressions(): Flow<List<ExpressionEntity>>

    @Insert(entity = ExpressionEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertExpression(expressionEntity: ExpressionEntity)

    @Query("SELECT * FROM expression_table WHERE id = :id")
    suspend fun getExpressionById(id: Int): ExpressionEntity

    @Query("SELECT id FROM expression_table")
    suspend fun getExpressionsIds(): List<Int>

    @Update
    suspend fun updateExpression(expressionEntity: ExpressionEntity)

    @Query("SELECT * FROM expression_table WHERE isFavorite = 1")
    fun getFavoritesExpressions(): Flow<List<ExpressionEntity>>
}