package hu.toliver.whacook.data.repository

import hu.toliver.whacook.data.local.dao.RecipeDao
import hu.toliver.whacook.data.local.entity.toDomain
import hu.toliver.whacook.data.local.entity.toEntity
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.repository.IRecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRecipeRepository(private val dao: RecipeDao) : IRecipeRepository {
    override fun getRecipes(): Flow<List<Recipe>> {
        return dao.getAllRecipes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        dao.insertRecipe(recipe.toEntity())
    }

    override suspend fun deleteRecipe(id: String) {
        dao.deleteRecipeById(id)
    }

    override suspend fun getRecipe(id: String): Recipe? {
        return dao.getRecipeById(id)?.toDomain()
    }
}

