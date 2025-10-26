package hu.toliver.whacook.data.di

import hu.toliver.whacook.APIKey
import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {
    single { HttpClient() }
    single { GeminiRemoteDataSource(get(), apiKey = APIKey().invoke()) }
    single<GeminiRecipeGenerationRepository> { GeminiRecipeGenerationRepository(get()) }
}