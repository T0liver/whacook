package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository

class RecepieGenerationUseCase (
    private val repository: IRecipeGenerationRepository
) {
    suspend operator fun invoke(prompt: String): String {
        return repository.generateResource(prompt)
    }
}