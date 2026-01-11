package hu.toliver.whacook.data.local

import hu.toliver.whacook.data.local.dao.SettingDao
import hu.toliver.whacook.data.local.entity.SettingEntity

class RecipePreferencesManager(private val settingDao: SettingDao) {

    companion object {
        const val RECIPE_PREFERENCES_KEY = "recipe_preferences"
    }

    suspend fun getPreferences(): String? = settingDao.getSetting(RECIPE_PREFERENCES_KEY)

    suspend fun savePreferences(preferences: String) {
        settingDao.insertSetting(SettingEntity(RECIPE_PREFERENCES_KEY, preferences))
    }
}

