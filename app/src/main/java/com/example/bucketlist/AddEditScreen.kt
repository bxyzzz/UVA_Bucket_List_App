package com.example.bucketlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableLongStateOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen (
    navController: NavController,
    viewModel: AddEditBucketItemViewModel = hiltViewModel()
) {
    // Source: https://www.baeldung.com/kotlin/current-date-time
    val calendar = Calendar.getInstance()
    calendar.set(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    )
    val nameState = viewModel.itemName.value
    val completedState = viewModel.itemIsCompleted.value
    val dueDateState = viewModel.itemDueDate.value

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditBucketItemViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditBucketItemViewModel.UiEvent.SaveItem -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        // Apparently Scaffold MUST take one composable, so I have
        // to add a larger composable container for the two FABS
        floatingActionButton = {
            Box(
                // I COULDN'T GET THIS TO CENTER AFTER 1HR SO I JUST
                // DID THE UGLY CODING WAY
                modifier = Modifier
                    .fillMaxWidth()
                    //.background(Color.Blue)
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Cancel Floating Action Button
                    FloatingActionButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel"
                        )
                    }

                    // Save Floating Action Button
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(AddEditBucketItemEvent.SaveBucketItem)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save item"
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = nameState.text,
                hint = nameState.hint,
                isHintVisible = nameState.isHintVisible,
                onValueChange = {
                    viewModel.onEvent(AddEditBucketItemEvent.EnteredName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBucketItemEvent.ChangeNameFocus(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineLarge

            )
            // SOURCE: https://stackoverflow.com/questions/68592618/how-to-add-border-on-bottom-only-in-jetpack-compose
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val selectedDate = remember { mutableLongStateOf(System.currentTimeMillis()) }
            val dateState =
                rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
            val openDialog = remember { mutableStateOf(false) }

            // Source: https://stackoverflow.com/questions/69328886/datepicker-textvalue-is-updating-to-one-day-prior-instead-of-date-selected
            val dateFormatter = remember { SimpleDateFormat("MM/dd/yyyy", Locale.US).apply {

                timeZone = calendar.timeZone
            } }
            val dateText = dateFormatter.format(dueDateState)

            // SOURCE: Outlined Text Field for DatePicker
            // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#OutlinedTextField(kotlin.String,kotlin.Function1,androidx.compose.ui.Modifier,kotlin.Boolean,kotlin.Boolean,androidx.compose.ui.text.TextStyle,kotlin.Function0,kotlin.Function0,kotlin.Function0,kotlin.Function0,kotlin.Boolean,androidx.compose.ui.text.input.VisualTransformation,androidx.compose.foundation.text.KeyboardOptions,androidx.compose.foundation.text.KeyboardActions,kotlin.Boolean,kotlin.Int,kotlin.Int,androidx.compose.foundation.interaction.MutableInteractionSource,androidx.compose.ui.graphics.Shape,androidx.compose.material.TextFieldColors)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Due Date: ",
                    style = MaterialTheme.typography.bodyLarge,
                )
                TextField(
                    value = dateText,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(Color.Transparent)
                        .clickable {
                            openDialog.value = true
                        },
                    enabled = false,
                    readOnly = true,
                    onValueChange = {},
                )
            }

            if (openDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // I keep having this issue with the day behind a whole day behind so I'm adding 12h to make it 12:00 instead of 0:00 of the day
                                viewModel.onEvent(AddEditBucketItemEvent.ChangeDate((dateState.selectedDateMillis!!)+12*60*60*1000))
                                openDialog.value = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = dateState,
                    )
                    // more source
                    // https://semicolonspace.com/jetpack-compose-date-picker-material3/
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Completed: ",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f) // Make it bigger
                )
                Checkbox(
                    checked = completedState,
                    onCheckedChange = {
                        viewModel.onEvent(AddEditBucketItemEvent.ChangeCompleted(it, calendar.timeInMillis))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
