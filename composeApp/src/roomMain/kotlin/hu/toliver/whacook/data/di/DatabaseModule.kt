package hu.toliver.whacook.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import hu.toliver.whacook.data.local.AppDatabase
import hu.toliver.whacook.data.local.MIGRATION_1_2
import hu.toliver.whacook.data.local.getDatabaseBuilder
import hu.toliver.whacook.data.repository.RoomDatabaseRepository
import hu.toliver.whacook.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

actual val databaseModule = module {
    single<AppDatabase> {
        getDatabaseBuilder()
            .addMigrations(MIGRATION_1_2)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single<DatabaseRepository> { RoomDatabaseRepository(get()) }
}

