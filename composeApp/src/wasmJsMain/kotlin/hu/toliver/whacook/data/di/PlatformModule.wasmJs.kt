package hu.toliver.whacook.data.di

import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

actual val platformModule = module {
    single<Settings> {
        StorageSettings()
    }
}
