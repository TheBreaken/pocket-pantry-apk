package com.example.pocketpantry.feature.pantry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.feature.pantry.viewmodel.PantryViewModel

@Composable
fun PantryScreen(
    contentPadding: PaddingValues,
    onAddItem: () -> Unit,
    onEditItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PantryViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Text("Pantry", style = MaterialTheme.typography.headlineMedium)
        Text("Items: ${state.items.size} • Filter: ${state.filter}", style =  MaterialTheme.typography.bodyMedium)
        Text("TODO: list + search + filter chips")
    }
}
