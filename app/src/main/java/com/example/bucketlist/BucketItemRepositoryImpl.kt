package com.example.bucketlist

import kotlinx.coroutines.flow.Flow

class BucketItemRepositoryImpl (
    private val dao: BucketItemDao
) : BucketItemRepository {
    override fun getAllBucketItems(): Flow<List<BucketItem>> {
        return dao.getAllBucketItems()
    }

    override suspend fun getByID(id: Int): BucketItem? {
        return dao.getByID(id)
    }

    override suspend fun insertBucketItem(bucketItem : BucketItem) {
        return dao.insertBucketItem(bucketItem)
    }

    override suspend fun updateBucketItem(bucketItem : BucketItem) {
        return dao.updateBucketItem(bucketItem)
    }

    override suspend fun deleteBucketItem(bucketItem : BucketItem) {
        return dao.deleteBucketItem(bucketItem)
    }

}