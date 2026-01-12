package hu.toliver.whacook.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import hu.toliver.whacook.data.local.AppDatabase
import hu.toliver.whacook.data.local.MIGRATION_1_2
import hu.toliver.whacook.data.local.getDatabaseBuilder
import hu.toliver.whacook.data.repository.RoomDatabaseRepository
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers

private val repositoryInstance: DatabaseRepository by lazy {
    val db = getDatabaseBuilder()
        .addMigrations(MIGRATION_1_2)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    RoomDatabaseRepository(db)
}

actual fun provideDatabaseRepository(): DatabaseRepository = repositoryInstance

