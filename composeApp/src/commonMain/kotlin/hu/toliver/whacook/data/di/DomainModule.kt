package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase

/**
 * Returns a configured [RecepieGenerationUseCase].
 *
 * This provider delegates to [createRecipeGenerationUseCase] for the concrete
 * implementation. Call sites should obtain the use case via this function so
 * dependency wiring remains centralized and testable.
 *
 * @return an instance of [RecepieGenerationUseCase]
 */
fun provideRecepieGenerationUseCase(
    repository: hu.toliver.whacook.domain.repository.IRecipeGenerationRepository
): RecepieGenerationUseCase = createRecipeGenerationUseCase(repository)
