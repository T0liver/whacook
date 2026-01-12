package hu.toliver.whacook.data.repository

import hu.toliver.whacook.data.local.AppDatabase
import hu.toliver.whacook.data.local.entity.RecipeEntity
import hu.toliver.whacook.data.local.entity.SettingEntity
import hu.toliver.whacook.data.local.entity.toCommon
import hu.toliver.whacook.data.local.entity.toRoom
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDatabaseRepository(
    private val db: AppDatabase
) : DatabaseRepository {

    override fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return db.recipeDao().getAllRecipes().map { list ->
            list.map { it.toCommon() }
        }
    }

    override suspend fun getRecipeById(id: String): RecipeEntity? {
        return db.recipeDao().getRecipeById(id)?.toCommon()
    }

    override suspend fun insertRecipe(recipe: RecipeEntity) {
        db.recipeDao().insertRecipe(recipe.toRoom())
    }

    override suspend fun deleteRecipeById(id: String) {
        db.recipeDao().deleteRecipeById(id)
    }

    override fun getAllSettings(): Flow<List<SettingEntity>> {
        return db.settingDao().getAllSettings().map { list ->
            list.map { it.toCommon() }
        }
    }

    override suspend fun getSettingByKey(key: String): SettingEntity? {
        return db.settingDao().getSetting(key)?.toCommon()
    }

    override suspend fun insertSetting(setting: SettingEntity) {
        db.settingDao().insertSetting(setting.toRoom())
    }

    override suspend fun deleteSettingByKey(key: String) {
        db.settingDao().deleteSetting(key)
    }
}

