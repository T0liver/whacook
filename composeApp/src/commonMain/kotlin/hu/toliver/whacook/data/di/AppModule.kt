package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.usecase.RecepieUseCase
import org.koin.dsl.module

val appModule = module {
    single { createRecipeGenerationUseCase() }
    single { RecepieUseCase() }
}