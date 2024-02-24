package com.example.bucketlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BucketItemDao {

    @Query("SELECT * FROM bucketitem")
    fun getAllBucketItems(): Flow<List<BucketItem>>

    @Query("SELECT * FROM bucketitem WHERE id=:id ")
    suspend fun getByID(id: Int): BucketItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBucketItem(bucketItem : BucketItem)

    @Update
    suspend fun updateBucketItem(bucketItem : BucketItem)

    @Delete
    suspend fun deleteBucketItem(bucketItem : BucketItem)
}