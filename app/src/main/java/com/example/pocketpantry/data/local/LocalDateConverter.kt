package com.example.pocketpantry.data.local

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromEpochDays(value: Long?): LocalDate? = value?.let(LocalDate::ofEpochDay)

    @TypeConverter
    fun toEpochDays(value: LocalDate?): Long? = value?.toEpochDay()
}
