package hu.toliver.whacook.data.di

import hu.toliver.whacook.data.repository.DatabaseRepositoryImpl
import hu.toliver.whacook.domain.repository.DatabaseRepository

private val repositoryInstance: DatabaseRepository by lazy {
    DatabaseRepositoryImpl()
}

actual fun provideDatabaseRepository(): DatabaseRepository = repositoryInstance

