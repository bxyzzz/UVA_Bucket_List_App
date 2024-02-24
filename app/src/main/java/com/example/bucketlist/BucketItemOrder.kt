package com.example.bucketlist

sealed class BucketItemOrder (val orderType : OrderType){
    class Date(orderType: OrderType) : BucketItemOrder(orderType=OrderType.Ascending) // Default is ascending

}