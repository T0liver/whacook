package hu.toliver.whacook.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

lateinit var applicationContext: Context

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = applicationContext.getDatabasePath("recipe_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath
    )
}