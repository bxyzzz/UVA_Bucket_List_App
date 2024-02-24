package com.example.bucketlist

sealed class OrderType { // Set Ascending or Descending Order
    object Ascending: OrderType()
    object Descending: OrderType()
}