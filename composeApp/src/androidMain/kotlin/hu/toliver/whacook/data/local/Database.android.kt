package hu.toliver.whacook.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

lateinit var databaseApplicationContext: Context

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = databaseApplicationContext.getDatabasePath("recipe_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = databaseApplicationContext,
        name = dbFile.absolutePath
    )
}