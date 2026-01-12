package hu.toliver.whacook.data.repository

import hu.toliver.whacook.data.local.AppDatabase
import hu.toliver.whacook.data.local.entity.RecipeEntity
import hu.toliver.whacook.data.local.entity.SettingEntity
import hu.toliver.whacook.data.local.entity.toCommon
import hu.toliver.whacook.data.local.entity.toRoom
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RoomDatabaseRepository(
    private val db: AppDatabase
) : DatabaseRepository {

    override fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return db.recipeDao().getAllRecipes().map { list ->
            list.map { it.toCommon() }
        }.catch { e ->
            println("[RoomDatabaseRepository] Error fetching recipes: ${e.message}")
            emit(emptyList())
        }
    }

    override suspend fun getRecipeById(id: String): RecipeEntity? {
        return try {
            db.recipeDao().getRecipeById(id)?.toCommon()
        } catch (e: Exception) {
            println("[RoomDatabaseRepository] Error fetching recipe by id $id: ${e.message}")
            null
        }
    }

    override suspend fun insertRecipe(recipe: RecipeEntity) {
        try {
            db.recipeDao().insertRecipe(recipe.toRoom())
        } catch (e: Exception) {
            println("[RoomDatabaseRepository] Error inserting recipe: ${e.message}")
        }
    }

    override suspend fun deleteRecipeById(id: String) {
        try {
            db.recipeDao().deleteRecipeById(id)
        } catch (e: Exception) {
            println("[RoomDatabaseRepository] Error deleting recipe $id: ${e.message}")
        }
    }

    override fun getAllSettings(): Flow<List<SettingEntity>> {
        return db.settingDao().getAllSettings().map { list ->
            list.map { it.toCommon() }
        }.catch { e ->
            println("[RoomDatabaseRepository] Error fetching settings: ${e.message}")
            emit(emptyList())
        }
    }

    override suspend fun getSettingByKey(key: String): SettingEntity? {
        return try {
            db.settingDao().getSetting(key)?.toCommon()
        } catch (e: Exception) {
            println("[RoomDatabaseRepository] Error fetching setting $key: ${e.message}")
            null
        }
    }

    override suspend fun insertSetting(setting: SettingEntity) {
        try {
            db.settingDao().insertSetting(setting.toRoom())
        } catch (e: Exception) {
            println("[RoomDatabaseRepository] Error inserting setting: ${e.message}")
        }
    }

    override suspend fun deleteSettingByKey(key: String) {
        try {
            db.settingDao().deleteSetting(key)
        } catch (e: Exception) {
            println("[RoomDatabaseRepository] Error deleting setting $key: ${e.message}")
        }
    }
}
