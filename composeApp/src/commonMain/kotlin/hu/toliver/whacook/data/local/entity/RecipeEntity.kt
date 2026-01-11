package hu.toliver.whacook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val tools: List<String>,
    val serving: String,
    val favourite: Boolean,
    val category: String,
    val timeToMake: Duration,
    val generationTime: String,
    val rating: Int
)