package com.example.speak_caucasus.data.db.userdata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.speak_caucasus.data.db.userdata.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Query("SELECT * FROM activity_table ORDER BY date DESC")
    fun getAllActivity(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity_table WHERE date >= :startDate ORDER BY date DESC")
    fun getActivityFromDate(startDate: Long): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity_table WHERE date = :startOfDay LIMIT 1")
    suspend fun getActivityByDate(startOfDay: Long): ActivityEntity?
}