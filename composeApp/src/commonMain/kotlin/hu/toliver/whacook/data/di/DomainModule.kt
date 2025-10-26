package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { RecepieGenerationUseCase(get()) }
}