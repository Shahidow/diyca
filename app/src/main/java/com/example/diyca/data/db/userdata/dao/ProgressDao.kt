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

    @Query("SELECT COUNT(*) FROM progress_table WHERE lessonId = :lessonId")
    fun getLessonCount(lessonId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM progress_table WHERE themeId = :themeId")
    fun getThemeCount(themeId: String): Flow<Int>

    @Query("SELECT taskId FROM progress_table WHERE lessonId = :lessonId")
    fun getLessonTaskIds(lessonId: String): Flow<List<String>>

    @Query("SELECT * FROM progress_table WHERE themeId = :themeId")
    fun getProgressByTheme(themeId: String): Flow<List<ProgressEntity>>
}