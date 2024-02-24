package com.example.bucketlist

class DeleteBucketItemUseCase (
    private val repository: BucketItemRepository
) {
    suspend operator fun invoke(bucketItem: BucketItem) {
        repository.deleteBucketItem(bucketItem)
    }
}