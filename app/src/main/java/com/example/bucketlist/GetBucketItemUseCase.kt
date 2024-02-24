package com.example.bucketlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Used Philip Lackner's tutorial as a guide about Clean Architecture

// Purpose of use case is to make sure the UI doesn't directly modify the database and add an intermediary

// https://www.youtube.com/watch?v=8YPXv7xKh2w

class GetBucketItemUseCase (
    private val repository: BucketItemRepository
) {

    // Use case is meant for just one public function in the first place
    // operator invoke lets us call the class as a function

    operator fun invoke(
        bucketItemOrder : BucketItemOrder = BucketItemOrder.Date(OrderType.Descending) // Default
    ): Flow<List<BucketItem>> {
        return repository.getAllBucketItems().map { bucketItems ->
            when(bucketItemOrder.orderType) {
                is OrderType.Ascending -> {
                    bucketItems.sortedBy { it.dueDate }
                }
                is OrderType.Descending-> {
                    bucketItems.sortedByDescending { it.dueDate }
                }
            }


        }
    }
}