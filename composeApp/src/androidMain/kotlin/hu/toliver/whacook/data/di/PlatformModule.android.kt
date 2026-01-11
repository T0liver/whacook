package hu.toliver.whacook.data.di

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import hu.toliver.whacook.data.local.AppDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<Settings> {
        AndroidSettings(androidContext().getSharedPreferences("whacook_preferences", 0))
    }
    single<RoomDatabase.Builder<AppDatabase>> {
        val context = androidContext()
        val dbFile = context.getDatabasePath("whacook.db")
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
    }
}
