package com.example.speak_caucasus.data.db.userdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.speak_caucasus.data.db.userdata.dao.ActivityDao
import com.example.speak_caucasus.data.db.userdata.dao.ProgressDao
import com.example.speak_caucasus.data.db.userdata.dao.RewardsDao
import com.example.speak_caucasus.data.db.userdata.entity.ActivityEntity
import com.example.speak_caucasus.data.db.userdata.entity.ProgressEntity
import com.example.speak_caucasus.data.db.userdata.entity.RewardEntity

@Database(
    entities = [ActivityEntity::class, RewardEntity::class, ProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun rewardsDao(): RewardsDao
    abstract fun progressDao(): ProgressDao
}