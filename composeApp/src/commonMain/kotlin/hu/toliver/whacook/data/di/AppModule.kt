package hu.toliver.whacook.data.di

import hu.toliver.whacook.data.local.APIKeyManager
import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { GeminiRemoteDataSource(get(), get()) }
    single<IRecipeGenerationRepository> { GeminiRecipeGenerationRepository(get()) }
    single { RecepieGenerationUseCase(get()) }
    single { RecepieUseCase() }
    single { APIKeyManager(get()) }
}