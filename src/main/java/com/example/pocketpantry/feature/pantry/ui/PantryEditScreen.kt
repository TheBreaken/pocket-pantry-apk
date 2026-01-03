package com.example.pocketpantry.feature.pantry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.feature.pantry.viewmodel.PantryEditViewModel

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

    }
}
