package com.example.pocketpantry.feature.pantry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.feature.pantry.viewmodel.PantryViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PantryScreen(
    contentPadding: PaddingValues,
    onEditItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    existingViewModel: PantryViewModel? = null
) {
    val application = LocalContext.current.applicationContext as PocketPantryApplication
    val actualViewModel: PantryViewModel = existingViewModel ?: viewModel(
        factory = PantryViewModel.provideFactory(application)
    )
    val state by actualViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Pantry", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Items: ${state.items.size} • Filter: ${state.filter}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
        }

        when {
            state.isLoading -> item {
                Text("Loading pantry...", style = MaterialTheme.typography.bodyMedium)
            }

            state.items.isEmpty() -> item {
                Text(
                    "No pantry items yet. Add your first one!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> items(
                items = state.items,
                key = { item -> item.id ?: item.name.hashCode().toLong() }
            ) { item ->
                PantryItemRow(
                    item = item,
                    onEdit = onEditItem,
                    onDelete = actualViewModel::deleteItem
                )
            }
        }
    }
}

@Composable
private fun PantryItemRow(
    item: PantryItemUi,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    ElevatedCard(
        onClick = { item.id?.let(onEdit) },
        enabled = item.id != null,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Qty: ${item.quantityLabel}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                item.expiryDate?.let { date ->
                    Text(
                        text = "Expires: ${date.format(DateTimeFormatter.ofPattern(
                            "d MMM, yyyy",
                            Locale.getDefault()
                        ))}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                StatusLabel(item)
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { item.id?.let(onDelete) },
                enabled = item.id != null
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete pantry item",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun StatusLabel(item: PantryItemUi) {
    val (label, color) = when {
        item.isExpired -> "Expired" to MaterialTheme.colorScheme.error
        item.isExpiringSoon -> "Expiring soon" to MaterialTheme.colorScheme.tertiary
        else -> null to null
    }

    if (label != null && color != null) {
        Surface(
            color = color.copy(alpha = 0.15f),
            contentColor = color,
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}
