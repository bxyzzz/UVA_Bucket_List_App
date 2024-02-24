package com.example.bucketlist

import androidx.compose.ui.focus.FocusState
import java.util.Date

sealed class AddEditBucketItemEvent {
    data class EnteredName(val value: String) : AddEditBucketItemEvent()
    data class ChangeNameFocus(val focusState: FocusState) : AddEditBucketItemEvent()
    data class ChangeDate (val date: Long) : AddEditBucketItemEvent()
    data class ChangeCompleted(val completed: Boolean, val completedDate: Long) : AddEditBucketItemEvent()

    object SaveBucketItem: AddEditBucketItemEvent()
}
