package com.example.pocketpantry.feature.shopping.ui

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.feature.shopping.viewmodel.ShoppingViewModel

@Composable
fun ShoppingScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    existingViewModel: ShoppingViewModel? = null
) {
    val application = LocalContext.current.applicationContext as PocketPantryApplication
    val viewModel: ShoppingViewModel = existingViewModel ?: viewModel(
        factory = ShoppingViewModel.provideFactory(application)
    )
    val state by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Text("Shopping", style = MaterialTheme.typography.headlineMedium)
        Text("Items: ${state.items.size}", style = MaterialTheme.typography.bodyMedium)

        when {
            state.isLoading -> Text("Loading shopping list...")
            state.items.isEmpty() -> Text("No shopping list items yet.")
            else -> Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                state.items.forEach { item ->
                    val status = if (item.isChecked) " (done)" else ""
                    Text("- ${item.name} (${item.quantityLabel})$status")
                }
            }
        }

        Text("TODO: shopping list + check off + share")
    }
}
