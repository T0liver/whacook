package hu.toliver.whacook.data.di

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<Settings> {
        AndroidSettings(androidContext().getSharedPreferences("whacook_preferences", 0))
    }
}
