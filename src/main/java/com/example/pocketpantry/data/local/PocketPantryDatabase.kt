package com.example.pocketpantry.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pocketpantry.data.pantry.PantryDao
import com.example.pocketpantry.data.pantry.PantryEntity
import com.example.pocketpantry.data.shopping.ShoppingDao
import com.example.pocketpantry.data.shopping.ShoppingEntity

@Database(
    entities = [
        PantryEntity::class,
        ShoppingEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class PocketPantryDatabase : RoomDatabase() {
    abstract fun pantryDao(): PantryDao
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        fun build(context: Context): PocketPantryDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PocketPantryDatabase::class.java,
                "pocket_pantry.db"
            ).fallbackToDestructiveMigration(true).build()
        }
    }
}
