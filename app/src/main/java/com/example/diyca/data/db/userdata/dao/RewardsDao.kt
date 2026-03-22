package com.example.diyca.data.db.userdata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diyca.data.db.userdata.entity.RewardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RewardsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: RewardEntity)

    @Query("SELECT * FROM rewards_table")
    fun getAllRewards(): Flow<List<RewardEntity>>
}