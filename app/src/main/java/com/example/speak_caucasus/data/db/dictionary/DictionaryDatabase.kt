package com.example.speak_caucasus.data.db.dictionary

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.speak_caucasus.data.db.dictionary.dao.ExpressionDao
import com.example.speak_caucasus.data.db.dictionary.dao.PhrasebookDao
import com.example.speak_caucasus.data.db.dictionary.dao.PhrasebookItemDao
import com.example.speak_caucasus.data.db.dictionary.dao.ProverbDao
import com.example.speak_caucasus.data.db.dictionary.dao.WordDao
import com.example.speak_caucasus.data.db.dictionary.entity.ExpressionEntity
import com.example.speak_caucasus.data.db.dictionary.entity.PhrasebookEntity
import com.example.speak_caucasus.data.db.dictionary.entity.PhrasebookItemEntity
import com.example.speak_caucasus.data.db.dictionary.entity.ProverbEntity
import com.example.speak_caucasus.data.db.dictionary.entity.WordEntity


@Database(
    entities = [
        WordEntity::class,
        ProverbEntity::class,
        ExpressionEntity::class,
        PhrasebookItemEntity::class,
        PhrasebookEntity::class
    ],
    version = 1
)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun proverbDao(): ProverbDao
    abstract fun expressionDao(): ExpressionDao
    abstract fun phrasebookItemDao(): PhrasebookItemDao
    abstract fun phrasebookDao(): PhrasebookDao
}