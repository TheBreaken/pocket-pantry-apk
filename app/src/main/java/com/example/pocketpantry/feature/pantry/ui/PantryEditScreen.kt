package com.example.pocketpantry.feature.pantry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.feature.pantry.viewmodel.PantryEditViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryEditScreen(
    itemId: Long?,
    contentPadding: PaddingValues,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    existingViewModel: PantryEditViewModel? = null
) {
    val application = LocalContext.current.applicationContext as PocketPantryApplication
    val viewModel: PantryEditViewModel = existingViewModel ?: viewModel(
        factory = PantryEditViewModel.provideFactory(application)
    )
    val state by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("d MMM, yyyy") }
    val expiryDisplay = state.expiryDate?.format(dateFormatter) ?: ""
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.expiryDate?.toEpochMillis()
    )

    datePickerState.selectedDateMillis = state.expiryDate?.toEpochMillis()

    LaunchedEffect(itemId) {
        viewModel.load(itemId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(if (itemId == null) "add item" else "edit item")
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(contentPadding)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::setName,
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.quantityLabel,
                onValueChange = viewModel::setQuantityLabel,
                label = { Text("Quantity Label") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = expiryDisplay,
                onValueChange = {},
                readOnly = true,
                label = { Text("Expiry Date") },
                placeholder = { Text("Not set") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = "Select expiry date"
                        )
                    }
                }
            )

            Button(
                onClick = {
                    viewModel.save()
                    onDone()
                },
                enabled = state.canSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("save")
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val millis = datePickerState.selectedDateMillis
                            val date = millis?.toLocalDate()
                            viewModel.setExpiryDate(date)
                            showDatePicker = false
                        },
                        enabled = datePickerState.selectedDateMillis != null
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Row {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = {
                            viewModel.setExpiryDate(null)
                            datePickerState.selectedDateMillis = null
                            showDatePicker = false
                        }) {
                            Text("Clear")
                        }
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

private fun LocalDate?.toEpochMillis(): Long? = this?.atStartOfDay(ZoneOffset.UTC)
    ?.toInstant()
    ?.toEpochMilli()

private fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()
