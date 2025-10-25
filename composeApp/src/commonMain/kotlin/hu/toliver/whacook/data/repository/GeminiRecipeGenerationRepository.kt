package hu.toliver.whacook.data.repository

import hu.toliver.whacook.data.remote.GeminiRemoteDataSource
import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository

class GeminiRecipeGenerationRepository (
    private val remoteDataSource: GeminiRemoteDataSource
): IRecipeGenerationRepository {
    override suspend fun generateResource(userInput: String, context: Map<String, Any>): String {
        return remoteDataSource.generate(userInput, context)
    }
}