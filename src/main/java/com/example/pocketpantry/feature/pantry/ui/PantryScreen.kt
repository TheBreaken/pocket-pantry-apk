package com.example.pocketpantry.feature.pantry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.feature.pantry.viewmodel.PantryViewModel
import com.example.pocketpantry.ui.theme.Typography

@Composable
fun PantryScreen(
    contentPadding: PaddingValues,
    onAddItem: () -> Unit,
    onEditItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PantryViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Text("+")
            }
        }
    ) { innerPadding ->
        ColumnPlaceholder(
            title = "Pantry",
            subtitle = "Items: ${state.items.size} • Filter: ${state.filter}"
        )
    }
}

@Composable
private fun ColumnPlaceholder(title: String, subtitle: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(title, style = Typography.headlineMedium)
        Text(subtitle, style = Typography.bodyMedium)
        Text("TODO: list + search + filter chips")
    }
}
