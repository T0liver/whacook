package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.repository.DatabaseRepository

expect fun provideDatabaseRepository(): DatabaseRepository

