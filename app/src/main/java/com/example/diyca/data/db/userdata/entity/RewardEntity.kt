package com.example.diyca.data.db.userdata.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards_table")
data class RewardEntity(
    @PrimaryKey
    val rewardId: String,
    val imageUrl: String?,
    val title: String,
    val name: String,
    val category: String
)