package com.example.bucketlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BucketItemViewModel @Inject constructor(
    private val allBucketItemUseCases: AllBucketItemUseCases
): ViewModel() {

    private val _state = mutableStateOf<BucketItemState>(BucketItemState())
    val state: State<BucketItemState> = _state

    private var getBucketItemsJob: Job? = null

    private fun getBucketItems (bucketItemOrder : BucketItemOrder) {
        //1:05:30 might need to add Job to fix coroutine

        allBucketItemUseCases.getBucketItems(bucketItemOrder)
            // 2/23/24 So I'm gonna need to sort things twice I think
            // First is uncompleted to completed
            // Then is by OrderType.Ascending

            .onEach { bucketItems ->
                // https://www.baeldung.com/kotlin/sort-collection-multiple-fields
                val sortedList = bucketItems.sortedWith(compareBy<BucketItem> { it.isCompleted }.thenBy { it.dueDate })
                _state.value = state.value.copy (
                    bucketItems = sortedList,
                    bucketOrder = bucketItemOrder
                )
            }
            .launchIn(viewModelScope)

    }

    init {
        getBucketItems(BucketItemOrder.Date(OrderType.Ascending))
    }

    fun onEvent(event: BucketItemEvent) {
        when(event) {
            is BucketItemEvent.Order -> {
                // might be buggy
                if (state.value.bucketOrder::class == event.bucketItemOrder::class &&
                    state.value.bucketOrder.orderType == event.bucketItemOrder.orderType
                ) {
                    return
                }

                getBucketItems(event.bucketItemOrder)
            }
            is BucketItemEvent.DeleteItem -> {
                viewModelScope.launch {
                    allBucketItemUseCases.deleteBucketItem(event.bucketItem)

                }
            }

            is BucketItemEvent.AddItem -> {
                viewModelScope.launch {
                    allBucketItemUseCases.addBucketItem(event.bucketItem)
                }
            }

            is BucketItemEvent.CompleteItem -> {
                viewModelScope.launch {
                    val updatedItem = state.value.bucketItems.find { it.id == event.itemId }?.copy(isCompleted = event.isCompleted)
                    updatedItem?.let {
                        allBucketItemUseCases.updateBucketItem(it)
                        updateLocalList(it)
                    }
                }
            }


        }
    }

    private fun updateLocalList(updatedItem: BucketItem) {
        val updatedList = state.value.bucketItems.toMutableList()
        val index = updatedList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            updatedList[index] = updatedItem
            _state.value = state.value.copy(bucketItems = updatedList)
        }
    }
}