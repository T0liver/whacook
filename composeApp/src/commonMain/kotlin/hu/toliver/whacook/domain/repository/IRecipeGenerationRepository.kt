package hu.toliver.whacook.domain.repository

interface IRecipeGenerationRepository {
    suspend fun generateResource(userInput: String): String
}