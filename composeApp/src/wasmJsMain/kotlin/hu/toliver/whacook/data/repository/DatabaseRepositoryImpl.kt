package hu.toliver.whacook.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import hu.toliver.whacook.data.local.WebDatabaseFactory
import hu.toliver.whacook.data.local.entity.RecipeEntity
import hu.toliver.whacook.data.local.entity.SettingEntity
import hu.toliver.whacook.db.Recipes
import hu.toliver.whacook.db.Settings
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class DatabaseRepositoryImpl : DatabaseRepository {

    private suspend fun getQueries() = WebDatabaseFactory.getDatabase().webDatabaseQueries

    override fun getAllRecipes(): Flow<List<RecipeEntity>> = flow {
        try {
            val queries = getQueries()
            emitAll(
                queries.getAllRecipes()
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { list -> list.map { it.toEntity() } }
            )
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in getAllRecipes: ${e.message}")
            emit(emptyList())
        }
    }

    override suspend fun getRecipeById(id: String): RecipeEntity? {
        return try {
            getQueries().getRecipeById(id).executeAsOneOrNull()?.toEntity()
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in getRecipeById $id: ${e.message}")
            null
        }
    }

    override suspend fun insertRecipe(recipe: RecipeEntity) {
        try {
            getQueries().insertRecipe(recipe.toDbModel())
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in insertRecipe: ${e.message}")
        }
    }

    override suspend fun deleteRecipeById(id: String) {
        try {
            getQueries().deleteRecipeById(id)
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in deleteRecipeById $id: ${e.message}")
        }
    }

    override fun getAllSettings(): Flow<List<SettingEntity>> = flow {
        try {
            val queries = getQueries()
            emitAll(
                queries.getAllSettings()
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { list -> list.map { it.toEntity() } }
            )
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in getAllSettings: ${e.message}")
            emit(emptyList())
        }
    }

    override suspend fun getSettingByKey(key: String): SettingEntity? {
        return try {
            getQueries().getSetting(key).executeAsOneOrNull()?.toEntity()
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in getSettingByKey $key: ${e.message}")
            null
        }
    }

    override suspend fun insertSetting(setting: SettingEntity) {
        try {
            getQueries().insertSetting(setting.toDbModel())
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in insertSetting: ${e.message}")
        }
    }

    override suspend fun deleteSettingByKey(key: String) {
        try {
            getQueries().deleteSetting(key)
        } catch (e: Exception) {
            println("[DatabaseRepositoryImpl] Error in deleteSettingByKey $key: ${e.message}")
        }
    }

    // Mappers

    private fun Recipes.toEntity(): RecipeEntity {
        return RecipeEntity(
            id = id,
            name = name,
            ingredients = Json.decodeFromString(ingredients),
            steps = Json.decodeFromString(steps),
            tools = Json.decodeFromString(tools),
            serving = serving,
            favourite = favourite,
            category = category,
            timeToMake = Json.decodeFromString(timeToMake),
            generationTime = generationTime,
            rating = rating
        )
    }

    private fun RecipeEntity.toDbModel(): Recipes {
        return Recipes(
            id = id,
            name = name,
            ingredients = Json.encodeToString(ingredients),
            steps = Json.encodeToString(steps),
            tools = Json.encodeToString(tools),
            serving = serving,
            favourite = favourite,
            category = category,
            timeToMake = Json.encodeToString(timeToMake),
            generationTime = generationTime,
            rating = rating
        )
    }

    private fun Settings.toEntity(): SettingEntity {
        return SettingEntity(
            key = key,
            value = value_
        )
    }

    private fun SettingEntity.toDbModel(): Settings {
        return Settings(
            key = key,
            value_ = value
        )
    }
}

