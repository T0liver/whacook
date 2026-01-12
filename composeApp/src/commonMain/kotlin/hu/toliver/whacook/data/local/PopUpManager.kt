package hu.toliver.whacook.data.local

import hu.toliver.whacook.data.local.entity.SettingEntity
import hu.toliver.whacook.domain.repository.DatabaseRepository

class PopUpManager(private val repository: DatabaseRepository) {

    companion object {
        const val WELCOME_SHOWN = "welcome_shown"
    }

    suspend fun isWelcomeShown(): Boolean {
        // Return true if value is "true", otherwise false (even if null)
        return repository.getSettingByKey(WELCOME_SHOWN)?.value == "true"
    }

    suspend fun setWelcomeShown() {
        repository.insertSetting(SettingEntity(WELCOME_SHOWN, "true"))
    }
}

