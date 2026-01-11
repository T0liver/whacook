package hu.toliver.whacook.data.local

import androidx.room.TypeConverter
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStringList(value: String): MutableList<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    @TypeConverter
    fun toStringList(list: MutableList<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromIngredientList(value: String): MutableList<Ingredient> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    @TypeConverter
    fun toIngredientList(list: MutableList<Ingredient>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromDuration(value: String): Duration {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            Duration(0.0, "")
        }
    }

    @TypeConverter
    fun toDuration(duration: Duration): String {
        return Json.encodeToString(duration)
    }
}

