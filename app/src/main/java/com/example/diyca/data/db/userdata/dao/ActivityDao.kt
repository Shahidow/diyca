package com.example.diyca.data.db.userdata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diyca.data.db.userdata.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Query("SELECT * FROM activity_table ORDER BY date DESC")
    fun getAllActivity(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity_table WHERE date >= :date ORDER BY date DESC")
    fun getActivityFromDate(date: String): Flow<List<ActivityEntity>>

    @Query("DELETE FROM activity_table")
    suspend fun clearAllActivity()
}