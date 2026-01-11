package hu.toliver.whacook.data.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect val platformModule: Module

fun startAppKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(
        appModule,
        viewModelModule,
        platformModule,
    )
}

fun doInitKoin() = startAppKoin()

