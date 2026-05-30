package com.example.pocketpantry

import android.app.Application
import com.example.pocketpantry.data.AppContainer

class PocketPantryApplication : Application() {
    val appContainer: AppContainer by lazy {
        AppContainer(this)
    }
}
