package com.example.bucketlist

data class AllBucketItemUseCases(
    val getBucketItems : GetBucketItemUseCase,
    val deleteBucketItem: DeleteBucketItemUseCase,
    val addBucketItem: AddBucketItemUseCase,
    val getBucketItem: GetOneBucketItemUseCase,
    val updateBucketItem: UpdateBucketItemUseCase
)
