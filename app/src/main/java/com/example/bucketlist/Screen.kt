package com.example.bucketlist

sealed class Screen (val route: String) {
    object ListScreen: Screen("list_screen")
    object AddEditScreen: Screen("add_edit_screen")

}