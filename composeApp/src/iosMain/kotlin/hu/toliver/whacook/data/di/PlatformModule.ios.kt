package hu.toliver.whacook.data.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import hu.toliver.whacook.data.local.AppDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDefaults

actual val platformModule = module {
    single<Settings> {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
    single<RoomDatabase.Builder<AppDatabase>> {
        val dbFilePath = NSHomeDirectory() + "/whacook.db"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }
}
