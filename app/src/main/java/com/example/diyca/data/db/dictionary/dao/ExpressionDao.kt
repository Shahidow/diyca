package com.example.diyca.data.db.dictionary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diyca.data.db.dictionary.entity.ExpressionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpressionDao {
    @Query("SELECT * FROM expression_table")
    fun getExpressions(): Flow<List<ExpressionEntity>>

    @Insert(entity = ExpressionEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertExpression(expressionEntity: ExpressionEntity)

    @Update
    suspend fun updateExpression(expressionEntity: ExpressionEntity)

    @Query("SELECT * FROM expression_table WHERE isFavorite = 1")
    fun getFavoritesExpressions(): Flow<List<ExpressionEntity>>

    @Query("SELECT id FROM expression_table WHERE isFavorite = 1")
    suspend fun getFavoriteExpressionIds(): List<String>

    @Query("DELETE FROM expression_table")
    suspend fun clearAllExpressions()
}