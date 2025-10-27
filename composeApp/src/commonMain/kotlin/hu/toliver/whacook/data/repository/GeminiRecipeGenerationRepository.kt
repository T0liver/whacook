package hu.toliver.whacook.data.repository

import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository

/**
 * Repository implementation that delegates recipe/resource generation requests to
 * [GeminiRemoteDataSource].
 *
 * This repository implements [IRecipeGenerationRepository] and acts as an abstraction
 * between the domain layer and the remote data source. It forwards the user's input
 * to the remote service and returns the generated result.
 *
 * @property remoteDataSource The remote data source responsible for contacting the Gemini API
 * and producing generated resources.
 */
class GeminiRecipeGenerationRepository(
    private val remoteDataSource: GeminiRemoteDataSource
) : IRecipeGenerationRepository {

    /**
     * Generates a resource (e.g. a recipe) from the provided [userInput].
     *
     * This implementation delegates the actual generation to [GeminiRemoteDataSource.generate].
     * Any exceptions thrown by the remote data source (network, parsing, etc.) are propagated
     * to the caller so that callers can handle errors appropriately.
     *
     * @param userInput The input text describing what should be generated.
     * @return The generated resource as a [String].
     * @throws Exception Propagates exceptions from the remote data source (implementation-specific).
     */
    override suspend fun generateResource(userInput: String): String {
        return remoteDataSource.generate(userInput)
    }
}