package com.example.pocketpantry.feature.pantry.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object PantryTab: NavKey

@Serializable
data object PantryList: NavKey

@Serializable
data class PantryEdit(val id: Long?): NavKey