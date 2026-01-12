package hu.toliver.whacook.domain.repository

import hu.toliver.whacook.data.local.entity.RecipeEntity
import hu.toliver.whacook.data.local.entity.SettingEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    // Recipes
    fun getAllRecipes(): Flow<List<RecipeEntity>>
    suspend fun getRecipeById(id: String): RecipeEntity?
    suspend fun insertRecipe(recipe: RecipeEntity)
    suspend fun deleteRecipeById(id: String)

    // Settings
    fun getAllSettings(): Flow<List<SettingEntity>>
    suspend fun getSettingByKey(key: String): SettingEntity?
    suspend fun insertSetting(setting: SettingEntity)
    suspend fun deleteSettingByKey(key: String)
}

