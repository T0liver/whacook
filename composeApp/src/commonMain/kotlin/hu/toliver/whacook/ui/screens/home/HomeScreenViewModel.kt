package hu.toliver.whacook.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import hu.toliver.whacook.data.di.createRecipeGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieGenerationUseCase
import hu.toliver.whacook.domain.usecase.RecepieUseCase

class HomeScreenViewModel (
    private val recipeUseCase: RecepieUseCase,
    private val recepieGenerationUseCase: RecepieGenerationUseCase
) : ScreenModel {
    var state by mutableStateOf(HomeState(
        isLoading = true,
        error = null
    ))
        private set
}