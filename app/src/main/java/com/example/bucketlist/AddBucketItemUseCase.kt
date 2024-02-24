package com.example.bucketlist

import com.example.bucketlist.BucketItemRepository

class AddBucketItemUseCase (
    private val repository: BucketItemRepository
) {
    @Throws(InvalidBucketItemException::class)
    suspend operator fun invoke(bucketItem : BucketItem) {
        if (bucketItem.name.isBlank()) {
            throw InvalidBucketItemException("Must include a name!")
        }

        //if (bucketItem.dueDate <= 0) { // cant have negative date
        //    throw InvalidBucketItemException("Must include a valid due date!")
        //}

        repository.insertBucketItem(bucketItem)
    }



}