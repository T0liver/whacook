package hu.toliver.whacook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Recipe

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val ingredients: MutableList<Ingredient>,
    val steps: MutableList<String>,
    val tools: MutableList<String>,
    val serving: String,
    val favourite: Boolean,
    val category: String,
    val timeToMake: Duration,
    val generationTime: String,
    val rating: Int
)

fun RecipeEntity.toDomain() = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    steps = steps,
    tools = tools,
    serving = serving,
    favourite = favourite,
    category = category,
    timeToMake = timeToMake,
    generationTime = generationTime,
    rating = rating
)

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    ingredients = ingredients,
    steps = steps,
    tools = tools,
    serving = serving,
    favourite = favourite,
    category = category,
    timeToMake = timeToMake,
    generationTime = generationTime,
    rating = rating
)

