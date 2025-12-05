package hu.toliver.whacook.data.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(
        appModule,
        viewModelModule,
    )
}