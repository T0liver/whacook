package hu.toliver.whacook.data.di

import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.data.repository.GeminiRecipeGenerationRepository
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Data module providing simple, manual composition helpers for the data layer.
 *
 * These factory functions create HTTP client, remote data source, repository and
 * use-case instances without requiring a DI framework. They're intended to be
 * called from platform entry points (for example Android/iOS launch code) or
 * from view models in tests and simple apps.
 */

/**
 * Create a preconfigured [HttpClient] for network calls.
 *
 * The client has [ContentNegotiation] installed with JSON serialization that
 * is lenient and ignores unknown keys to permit forward-compatible parsing of
 * responses from external services.
 */
fun createHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = false
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

/**
 * Create a [GeminiRemoteDataSource] using the provided [client] and [preferencesManager].
 *
 * @param client HTTP client to use for network requests
 * @param preferencesManager manager for accessing stored preferences (API Key)
 * @return configured [GeminiRemoteDataSource]
 */
fun createGeminiRemoteDataSource(
    client: HttpClient = createHttpClient(),
    preferencesManager: hu.toliver.whacook.data.local.APIKeyManager
): GeminiRemoteDataSource {
    return GeminiRemoteDataSource(client, preferencesManager)
}

/**
 * Create a [GeminiRecipeGenerationRepository] backed by the given remote data source.
 *
 * @param remote remote data source used by the repository
 * @return a [GeminiRecipeGenerationRepository] instance
 */
fun createGeminiRecipeGenerationRepository(
    remote: GeminiRemoteDataSource
): GeminiRecipeGenerationRepository = GeminiRecipeGenerationRepository(remote)

/**
 * Create the recipe generation use case.
 *
 * @param repository repository used by the use case
 * @return a configured [RecepieGenerationUseCase]
 */
fun createRecipeGenerationUseCase(
    repository: hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
): RecepieGenerationUseCase = RecepieGenerationUseCase(repository)
