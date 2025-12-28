package com.example.pocketpantry.feature.shopping.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.feature.shopping.viewmodel.ShoppingViewModel

@Composable
fun ShoppingScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text("Shopping", style = typography.headlineMedium)
        Text("Items: ${state.items.size}", style = typography.bodyMedium)
        Text("TODO: shopping list + check off + share")
    }
}