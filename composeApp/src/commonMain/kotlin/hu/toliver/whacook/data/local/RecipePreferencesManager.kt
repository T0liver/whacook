package hu.toliver.whacook.data.local

import hu.toliver.whacook.data.local.entity.SettingEntity
import hu.toliver.whacook.domain.repository.DatabaseRepository

class RecipePreferencesManager(private val repository: DatabaseRepository) {

    companion object {
        const val RECIPE_PREFERENCES_KEY = "recipe_preferences"
    }

    suspend fun getPreferences(): String? = repository.getSettingByKey(RECIPE_PREFERENCES_KEY)?.value

    suspend fun savePreferences(preferences: String) {
        repository.insertSetting(SettingEntity(RECIPE_PREFERENCES_KEY, preferences))
    }
}

