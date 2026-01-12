package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.data.mapper.toDomain
import hu.toliver.whacook.data.mapper.toEntity
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A use case class that encapsulates operations related to creating and managing recipes.
 *
 * This class provides core functionality for handling recipe creation, editing metadata
 * (such as name, category, and rating), and serialization to and from JSON.
 *
 * Typical usage:
 * ```
 * val recipeUseCase = RecipeUseCase(repository)
 * val recipe = recipeUseCase() // Creates an empty recipe
 * recipeUseCase.rename(recipe, "Chocolate Cake")
 * recipeUseCase.rate(recipe, 5)
 * val json = recipeUseCase.save(recipe)
 * val restoredRecipe = recipeUseCase.load(json)
 * ```
 */
class RecepieUseCase(
    private val repository: DatabaseRepository
) {
    /**
     * Creates a new empty recipe instance.
     *
     * This is the default invoke operation for the class, allowing it to be called
     * like a function, e.g. `recipeUseCase()`.
     *
     * @return A new empty [Recipe] object.
     */
    operator fun invoke(): Recipe {
        return create()
    }

    /**
     * Creates and returns a new empty recipe.
     *
     * @return A [Recipe] initialized with default values.
     */
    fun create(): Recipe {
        return Recipe(
            id = "",
            name = "",
            ingredients = mutableListOf(),
            steps = mutableListOf(),
            tools = mutableListOf(),
            serving = "",
            favourite = false,
            category = "",
            timeToMake = Duration(0.0, ""),
            generationTime = "",
            rating = 0
        )
    }

    /**
     * Updates the favourite status of the given recipe.
     *
     * @param recipe The recipe to modify.
     * @param favourite The new favourite status (true or false).
     */
    fun refavour(recipe: Recipe, favourite: Boolean) {
        recipe.favourite = favourite
    }

    /**
     * Updates the name of the given recipe.
     *
     * @param recipe The recipe to rename.
     * @param name The new name for the recipe.
     */
    fun rename(recipe: Recipe, name: String) {
        recipe.name = name
    }

    /**
     * Changes the category of the given recipe.
     *
     * @param recipe The recipe to modify.
     * @param category The new category to assign.
     */
    fun recategorize(recipe: Recipe, category: String) {
        recipe.category = category
    }

    /**
     * Updates the preparation time for the recipe.
     *
     * @param recipe The recipe to modify.
     * @param length The duration value.
     * @param unit The time unit (e.g., "minutes", "hours").
     */
    fun retime(recipe: Recipe, length: Double, unit: String) {
        recipe.timeToMake = Duration(length, unit)
    }

    /**
     * Assigns a rating to the recipe on a scale from 0 to 5.
     *
     * @param recipe The recipe to rate.
     * @param rating The rating value, must be within the range 0–5.
     *
     * @throws IllegalArgumentException If the rating is outside the valid range.
     */
    fun rate(recipe: Recipe, rating: Int) {
        require(rating in 0..5) { "Rating must be between 0 and 5" }
        recipe.rating = rating
    }

    /**
     * Updates the serving information for the recipe.
     *
     * @param recipe The recipe to modify.
     * @param serving The serving description (e.g., "2 people", "4 portions").
     */
    fun reserve(recipe: Recipe, serving: String) {
        recipe.serving = serving
    }

    /**
     * Serializes the given recipe into a JSON-formatted string.
     *
     * @param recipe The recipe to serialize.
     * @return A JSON string representing the recipe.
     */
    fun save(recipe: Recipe): String {
        val json = Json.encodeToString(recipe)
        println(json)
        return json
    }

    suspend fun saveToDatabase(recipe: Recipe) {
        repository.insertRecipe(recipe.toEntity())
    }

    fun getAllFromDatabase(): Flow<List<Recipe>> {
        return repository.getAllRecipes().map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun deleteFromDatabase(recipe: Recipe) {
        repository.deleteRecipeById(recipe.id)
    }

    /**
     * Deserializes a JSON-formatted string into a [Recipe] object.
     *
     * @param json The JSON string to decode.
     * @return A [Recipe] reconstructed from the JSON data.
     */
    fun load(json: String): Recipe {
        val realJson = json.substringBeforeLast("}").substringAfter("{")
        return  Json.decodeFromString<Recipe>("{\n$realJson\n}")
    }
}