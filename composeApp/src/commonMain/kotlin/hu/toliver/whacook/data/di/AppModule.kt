package hu.toliver.whacook.data.di

import hu.toliver.whacook.data.local.PreferencesManager
import hu.toliver.whacook.data.local.AppDatabase
import hu.toliver.whacook.data.local.getRoomDatabase
import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import hu.toliver.whacook.data.repository.RoomRecipeRepository
import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
import hu.toliver.whacook.domain.repository.IRecipeRepository
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { GeminiRemoteDataSource(get(), get()) }
    single<IRecipeGenerationRepository> { GeminiRecipeGenerationRepository(get()) }
    single<IRecipeRepository> { RoomRecipeRepository(get()) }
    single { RecepieGenerationUseCase(get()) }
    single { RecepieUseCase() }
    single { PreferencesManager(get()) }
    single { get<AppDatabase>().recipeDao() }
    single { getRoomDatabase(get()) }

}