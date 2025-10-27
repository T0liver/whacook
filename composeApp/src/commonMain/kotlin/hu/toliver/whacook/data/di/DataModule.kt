package hu.toliver.whacook.data.di

import hu.toliver.whacook.APIKey
import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import io.ktor.client.HttpClient

/**
 * Manual composition helpers to create the data-layer objects without using a DI framework.
 * Use these from your platform entry points or view models to obtain instances.
 */

fun createHttpClient(): HttpClient = HttpClient()

fun createGeminiRemoteDataSource(client: HttpClient = createHttpClient()): GeminiRemoteDataSource {
    return GeminiRemoteDataSource(client, apiKey = APIKey().invoke())
}

fun createGeminiRecipeGenerationRepository(
    remote: GeminiRemoteDataSource = createGeminiRemoteDataSource()
): GeminiRecipeGenerationRepository = GeminiRecipeGenerationRepository(remote)

fun createRecepieGenerationUseCase(
    repository: hu.toliver.whacook.domain.repository.IRecipeGenerationRepository =
        createGeminiRecipeGenerationRepository()
): RecepieGenerationUseCase = RecepieGenerationUseCase(repository)
