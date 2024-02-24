package com.example.bucketlist

import kotlinx.coroutines.flow.Flow

interface BucketItemRepository {

    fun getAllBucketItems(): Flow<List<BucketItem>>

    suspend fun getByID(id: Int): BucketItem?

    suspend fun insertBucketItem(bucketItem : BucketItem)

    suspend fun updateBucketItem(bucketItem : BucketItem)

    suspend fun deleteBucketItem(bucketItem : BucketItem)

}