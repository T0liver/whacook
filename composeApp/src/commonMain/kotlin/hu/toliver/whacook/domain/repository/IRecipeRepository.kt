package hu.toliver.whacook.domain.repository

import hu.toliver.whacook.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface IRecipeRepository {
    fun getRecipes(): Flow<List<Recipe>>
    suspend fun saveRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: String)
    suspend fun getRecipe(id: String): Recipe?
}

