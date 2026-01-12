package hu.toliver.whacook.data.di

import hu.toliver.whacook.data.repository.DatabaseRepositoryImpl
import hu.toliver.whacook.domain.repository.DatabaseRepository
import org.koin.dsl.module

actual val databaseModule = module {
    single<DatabaseRepository> { provideDatabaseRepository() }
}
