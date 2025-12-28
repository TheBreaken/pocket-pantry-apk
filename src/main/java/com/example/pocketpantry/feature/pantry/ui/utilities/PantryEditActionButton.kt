package com.example.pocketpantry.feature.pantry.ui.utilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.pocketpantry.feature.pantry.navigation.PantryEdit
import com.example.pocketpantry.feature.pantry.navigation.PantryList

@Composable
fun PantryEditActionButton(
    backStack: NavBackStack<NavKey>,
    visible: Boolean,
    color: Color = MaterialTheme.colorScheme.primary,
    icon: ImageVector = Icons.Default.Add,
    contentDescription: String = "Add new pantry item"
) {
    AnimatedVisibility(
        visible = visible && backStack.lastOrNull() == PantryList,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
    ) {
        FloatingActionButton(
            onClick = { backStack.add(PantryEdit(id = null)) },
            containerColor = color
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    }
}