package com.example.bucketlist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BucketItem::class],
    version = 1
)

abstract class BucketItemDatabase: RoomDatabase() {

    abstract val bucketItemDao: BucketItemDao

    companion object {
        const val DATABASE_NAME = "bucketlist_db"
    }
}