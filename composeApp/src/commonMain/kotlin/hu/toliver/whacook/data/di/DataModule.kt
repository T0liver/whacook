package hu.toliver.whacook.data.di

import hu.toliver.whacook.BuildKonfig
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
 * Create a [GeminiRemoteDataSource] using the provided [client].
 *
 * By default, this function will create a new HTTP client and obtain the API key
 * from [BuildKonfig]. Supply a different [client] for tests or to share a client
 * across other callers.
 *
 * @param client HTTP client to use for network requests
 * @return configured [GeminiRemoteDataSource]
 */
fun createGeminiRemoteDataSource(client: HttpClient = createHttpClient()): GeminiRemoteDataSource {
    return GeminiRemoteDataSource(client, apiKey = BuildKonfig.GEMINI_API_KEY)
}

/**
 * Create a [GeminiRecipeGenerationRepository] backed by the given remote data source.
 *
 * @param remote remote data source used by the repository
 * @return a [GeminiRecipeGenerationRepository] instance
 */
fun createGeminiRecipeGenerationRepository(
    remote: GeminiRemoteDataSource = createGeminiRemoteDataSource()
): GeminiRecipeGenerationRepository = GeminiRecipeGenerationRepository(remote)

/**
 * Create the recipe generation use case.
 *
 * The function name is kept as `createRecGenerationUseCase` to match the
 * existing public API in the module. The created [RecepieGenerationUseCase]
 * depends on an [hu.toliver.whacook.domain.repository.IRecipeGenerationRepository], which by default is provided by
 * [createGeminiRecipeGenerationRepository].
 *
 * @param repository repository used by the use case
 * @return a configured [RecepieGenerationUseCase]
 */
fun createRecipeGenerationUseCase(
    repository: hu.toliver.whacook.domain.repository.IRecipeGenerationRepository =
        createGeminiRecipeGenerationRepository()
): RecepieGenerationUseCase = RecepieGenerationUseCase(repository)
