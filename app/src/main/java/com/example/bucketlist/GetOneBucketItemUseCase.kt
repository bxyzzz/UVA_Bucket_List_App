package com.example.bucketlist

class GetOneBucketItemUseCase (
    private val repository: BucketItemRepository
) {
    suspend operator fun invoke(id: Int): BucketItem? {
        return repository.getByID(id)
    }
}