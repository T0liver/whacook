package hu.toliver.whacook.data.local.entity
)
    rating = rating
    generationTime = generationTime,
    timeToMake = timeToMake,
    category = category,
    favourite = favourite,
    serving = serving,
    tools = tools,
    steps = steps,
    ingredients = ingredients,
    name = name,
    id = id,
fun RecipeEntity.toRoom() = RoomRecipeEntity(

)
    rating = rating
    generationTime = generationTime,
    timeToMake = timeToMake,
    category = category,
    favourite = favourite,
    serving = serving,
    tools = tools,
    steps = steps,
    ingredients = ingredients,
    name = name,
    id = id,
fun RoomRecipeEntity.toCommon() = RecipeEntity(

)
    val rating: Int
    val generationTime: String,
    val timeToMake: Duration,
    val category: String,
    val favourite: Boolean,
    val serving: String,
    val tools: List<String>,
    val steps: List<String>,
    val ingredients: List<Ingredient>,
    val name: String,
    @PrimaryKey val id: String,
data class RoomRecipeEntity(
@Entity(tableName = "recipes")

import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Duration
import androidx.room.PrimaryKey
import androidx.room.Entity


