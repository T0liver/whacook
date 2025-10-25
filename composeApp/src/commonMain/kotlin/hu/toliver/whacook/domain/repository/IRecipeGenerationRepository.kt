package hu.toliver.whacook.domain.repository

interface IRecipeGenerationRepository {
    suspend fun generateResource(userInput: String, context: Map<String, Any> = emptyMap()): String
}