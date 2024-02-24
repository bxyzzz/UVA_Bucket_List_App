package com.example.bucketlist

class UpdateBucketItemUseCase (
    private val repository: BucketItemRepository
) {
    suspend operator fun invoke(bucketItem : BucketItem) {
        repository.updateBucketItem(bucketItem)
    }
}