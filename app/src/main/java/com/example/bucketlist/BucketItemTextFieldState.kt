package com.example.bucketlist

data class BucketItemTextFieldState(
    // This is to implement hint text that changes state when clicked on
    // for adding a new Bucket Item
    val text : String = "",
    val hint : String = "",
    val isHintVisible: Boolean = true
)
