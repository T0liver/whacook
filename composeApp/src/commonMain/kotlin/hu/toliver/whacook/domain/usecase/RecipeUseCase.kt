package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.data.mapper.toDomain
import hu.toliver.whacook.data.mapper.toEntity
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
class RecipeUseCase(
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
     * Persists the given [Recipe] in the underlying database.
     *
     * @param recipe the domain [Recipe] to save, which will be mapped to its entity form.
     */
    suspend fun saveToDatabase(recipe: Recipe) {
        repository.insertRecipe(recipe.toEntity())
    }

    /**
     * Returns a [Flow] of all recipes stored in the database as domain models.
     *
     * The repository entities are mapped to [Recipe] objects before being emitted.
     *
     * @return a [Flow] emitting lists of [Recipe] instances.
     */
    fun getAllFromDatabase(): Flow<List<Recipe>> {
        return repository.getAllRecipes().map { list ->
            list.map { it.toDomain() }
        }
    }

    /**
     * Deletes the given [Recipe] from the database based on its `id`.
     *
     * @param recipe the [Recipe] whose persisted entry should be removed.
     */
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