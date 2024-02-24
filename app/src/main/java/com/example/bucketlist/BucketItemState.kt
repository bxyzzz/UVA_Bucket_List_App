package com.example.bucketlist

data class BucketItemState(
    val bucketItems : List<BucketItem> = emptyList(),
    val bucketOrder : BucketItemOrder = BucketItemOrder.Date(OrderType.Ascending)
)
