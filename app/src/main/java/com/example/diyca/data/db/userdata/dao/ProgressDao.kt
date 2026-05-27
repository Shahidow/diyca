package com.example.diyca.data.db.userdata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diyca.data.db.userdata.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity)

    @Query("SELECT * FROM progress_table")
    fun getAllProgress(): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM progress_table WHERE lessonId = :lessonId")
    fun getProgressByLesson(lessonId: String): Flow<List<ProgressEntity>>

    @Query("SELECT COUNT(taskId) FROM progress_table WHERE lessonId = :lessonId")
    fun getLessonTasksCount(lessonId: String): Flow<Int>

    @Query("SELECT * FROM progress_table WHERE themeId = :themeId")
    fun getProgressByTheme(themeId: String): Flow<List<ProgressEntity>>

    @Query("DELETE FROM progress_table")
    suspend fun clearAllProgress()
}