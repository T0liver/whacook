package hu.toliver.whacook.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import hu.toliver.whacook.data.local.APIKeyManager
import hu.toliver.whacook.data.local.AppDatabase
import hu.toliver.whacook.data.local.MIGRATION_1_2
import hu.toliver.whacook.data.local.getDatabaseBuilder
import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { GeminiRemoteDataSource(get(), get()) }
    single<IRecipeGenerationRepository> { GeminiRecipeGenerationRepository(get()) }
    single { RecepieGenerationUseCase(get()) }
    single { RecepieUseCase(get()) }
    single { APIKeyManager(get()) }

    single<AppDatabase> {
        getDatabaseBuilder()
            .addMigrations(MIGRATION_1_2)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single { get<AppDatabase>().recipeDao() }
    single { get<AppDatabase>().settingDao() }
}