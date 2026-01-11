package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.usecase.RecipeGenerationUseCase

/**
 * Returns a configured [RecipeGenerationUseCase].
 *
 * This provider delegates to [createRecipeGenerationUseCase] for the concrete
 * implementation. Call sites should obtain the use case via this function so
 * dependency wiring remains centralized and testable.
 *
 * @return an instance of [RecipeGenerationUseCase]
 */
fun provideRecepieGenerationUseCase(
    repository: hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
): RecipeGenerationUseCase = createRecipeGenerationUseCase(repository)
