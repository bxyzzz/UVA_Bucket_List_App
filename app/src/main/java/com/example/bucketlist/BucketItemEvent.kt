package com.example.bucketlist

sealed class BucketItemEvent {
    data class Order(val bucketItemOrder: BucketItemOrder) : BucketItemEvent()
    data class DeleteItem(val bucketItem: BucketItem): BucketItemEvent()
    data class AddItem(val bucketItem: BucketItem): BucketItemEvent()
    data class CompleteItem(val itemId: Int, val isCompleted: Boolean): BucketItemEvent()
}