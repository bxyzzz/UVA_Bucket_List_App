package com.example.bucketlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bucketlist.ui.theme.BucketListTheme
import dagger.hilt.android.AndroidEntryPoint

/*
PRIMARY SOURCE: I followed Philipp Lackner's Tutorial as a roadmap
https://www.youtube.com/watch?v=8YPXv7xKh2w
which is a Notes App Clean Architecture/CRUD app
I purposely chose something that was not directly a to-do list
because of the last line of the syllabus

The stuff about navControllers/Clean Architecture was ripped from
Philipp but everything else I did by hand
*/


// SECONDARY SOURCE:
// counters app from class
// https://github.com/cs-4720-uva/counters-app/blob/main/app/src/main/java/edu/virginia/cs/countersapp/MainActivity.kt

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BucketListTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListScreen.route,

                    ) {
                       composable(route = Screen.ListScreen.route) {
                           ListScreen(navController = navController)
                           // val viewModel: BucketItemViewModel = viewModel()
                       }
                        composable(
                            route = Screen.AddEditScreen.route +
                                    "?itemId={itemId}",
                            arguments = listOf(
                                navArgument(
                                    name = "itemId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            AddEditScreen(navController = navController)
                        }
                          //  ListScreen(navController = navController, viewModel = viewModel)

                            //val viewModel: BucketItemViewModel = viewModel()

                            //ListScreen(navController, viewModel)
                            /*


                        ) {

                    val sampleBucketItems = listOf(
                        BucketItem(
                            "asdpfauisdfi",
                            System.currentTimeMillis(),
                            false,
                            null,
                            1
                        ),
                        BucketItem(
                            "faspeipvc",
                            System.currentTimeMillis(),
                            false,
                            null,
                            2
                        ),
                        BucketItem(
                            "vacxopviouxc",
                            System.currentTimeMillis(),
                            true,
                            System.currentTimeMillis(),
                            3
                        )
                    )
                    Column {
                        sampleBucketItems.forEach { bucketItem ->
                            BucketListItem(
                                bucketItem = bucketItem
                            ) { isChecked ->
                            }
                        }

                    }

                     */
                        }
                    }
                }
            }
        }
    }
