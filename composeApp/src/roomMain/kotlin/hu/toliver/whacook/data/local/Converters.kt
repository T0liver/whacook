package hu.toliver.whacook.data.local

import androidx.room.TypeConverter
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toIngredientList(value: String): List<Ingredient> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromDuration(value: Duration): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toDuration(value: String): Duration {
        return Json.decodeFromString(value)
    }
}