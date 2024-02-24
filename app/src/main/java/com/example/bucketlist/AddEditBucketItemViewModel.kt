package com.example.bucketlist

import android.widget.DatePicker
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bucketlist.AddEditBucketItemViewModel.UiEvent.ShowSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditBucketItemViewModel @Inject constructor(
    private val bucketItemUseCases: AllBucketItemUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _itemName = mutableStateOf(BucketItemTextFieldState(
        hint = "Enter name..."
    ))
    val itemName: State<BucketItemTextFieldState> = _itemName

    private val _itemDueDate = mutableStateOf(Date())
    val itemDueDate: State<Date> = _itemDueDate

    private val _itemIsCompleted = mutableStateOf(false)
    val itemIsCompleted: State<Boolean> = _itemIsCompleted

    private val _itemCompletedDate = mutableStateOf(Date())
    val itemCompletedDate: State<Date> = _itemCompletedDate

    // E.g. if we rotate screen, we don't want to show snackbar again
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentBucketItemId: Int? = null

    // this logic was taken from Philipp, i struggled with this
    init {
        savedStateHandle.get<Int>("itemId")?.let { itemId ->
            if (itemId != -1) {
                viewModelScope.launch {
                    bucketItemUseCases.getBucketItem(itemId)?.also { item ->
                        currentBucketItemId = item.id
                        _itemName.value = itemName.value.copy (
                            text = item.name,
                            isHintVisible = false
                        )

                        _itemDueDate.value = Date(item.dueDate)

                        _itemIsCompleted.value = item.isCompleted

                    }
                }
            }

        }
    }

    fun onEvent(event: AddEditBucketItemEvent) {
        when (event) {
            is AddEditBucketItemEvent.EnteredName -> {
                _itemName.value = itemName.value.copy(
                    text = event.value
                )
            }
            is AddEditBucketItemEvent.ChangeNameFocus -> {
                _itemName.value = itemName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                    itemName.value.text.isBlank()
                )
            }

            is AddEditBucketItemEvent.ChangeDate -> {
                _itemDueDate.value = Date(event.date)
            }

            is AddEditBucketItemEvent.ChangeCompleted -> {
                _itemIsCompleted.value = event.completed
                _itemCompletedDate.value = Date(event.completedDate)

            }

            is AddEditBucketItemEvent.SaveBucketItem -> {
                viewModelScope.launch {
                    try { // If invalid date or empty name
                        bucketItemUseCases.addBucketItem(
                            BucketItem(
                                name = itemName.value.text,
                                dueDate = itemDueDate.value.time,
                                isCompleted = itemIsCompleted.value,
                                completedDate = itemCompletedDate.value.time,
                                id = currentBucketItemId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveItem)
                    } catch(e: InvalidBucketItemException){
                        _eventFlow.emit(
                            ShowSnackbar(
                                message = e.message ?: "Couldn't save item"

                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar (val message: String): UiEvent()
        object SaveItem: UiEvent()
    }
}