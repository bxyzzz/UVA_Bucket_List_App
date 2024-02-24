package com.example.bucketlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen (
    navController: NavController,
    viewModel: BucketItemViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // SOURCE: https://developer.android.com/jetpack/compose/designsystems/material2-material3#m3_8
    // SOURCE #2: https://developer.android.com/jetpack/compose/components/scaffold

    // Date Picker Source: https://medium.com/mobile-app-development-publication/date-and-time-picker-with-compose-9cadc4f50e6d
    Scaffold(
        //topBar = {
           // TopAppBar( // apparently this is experimental.... >:(
                // colors = topAppBarColors(
                   // containerColor = MaterialTheme.colorScheme.primaryContainer,
                   // titleContentColor = MaterialTheme.colorScheme.primary,
                // ),
                // title = {
                //    Text("My Bucket List:")
                 //}
          //  )
        //},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditScreen.route)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add item"
                )

            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues -> // Necessary so Snackbar & Floating Button don't overlap
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "My Bucket List:",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Content of screen (Lazy column of BucketList Items)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.bucketItems) { bucketItem ->
                    BucketListItem(
                        bucketItem = bucketItem,
                        onCheckedChange = { isChecked ->
                            bucketItem.id?.let {id -> // Is there an existing ID?
                            viewModel.onEvent(BucketItemEvent.CompleteItem(bucketItem.id, isChecked))
                            }
                        },
                        // FIX THIS
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()


                        /* onDeleteClick = {
                        viewModel.onEvent(BucketItemEvent.DeleteItem(bucketItem))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note deleted",
                            )
                        }
                        }*/
                        )
                    }
                }
            }
        }

    }