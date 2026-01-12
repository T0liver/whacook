package hu.toliver.whacook.data.di

import hu.toliver.whacook.data.local.APIKeyManager
import hu.toliver.whacook.data.local.PopUpManager
import hu.toliver.whacook.data.local.RecipePreferencesManager
import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
import hu.toliver.whacook.domain.usecase.RecipeGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecipeUseCase
import org.koin.dsl.module

val appModule = module {
    includes(databaseModule)
    single { createHttpClient() }
    single { GeminiRemoteDataSource(get(), get()) }
    single<IRecipeGenerationRepository> { GeminiRecipeGenerationRepository(get()) }
    single { RecipeGenerationUseCase(get(), get()) }
    single { RecipeUseCase(get<hu.toliver.whacook.domain.repository.DatabaseRepository>()) }
    single { APIKeyManager(get()) }
    single { RecipePreferencesManager(get()) }
    single { PopUpManager(get()) }
}