package com.example.bucketlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class BucketItem(
    val name : String,
    val dueDate: Long,
    val isCompleted : Boolean,
    val completedDate : Long?,
    @PrimaryKey val id: Int? = null
    )

// What happens if user tries to create an item without a name?
class InvalidBucketItemException(message : String) : Exception(message)