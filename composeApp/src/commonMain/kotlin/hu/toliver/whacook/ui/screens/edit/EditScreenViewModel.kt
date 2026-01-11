package hu.toliver.whacook.ui.screens.edit

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.domain.usecase.RecepieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditScreenViewModel(
    private val recipe: Recipe,
    private val recipeUseCase: RecepieUseCase
) : ScreenModel {

    private val _ingredients = mutableStateListOf<Ingredient>().apply {
        addAll(recipe.ingredients.map { it.copy() })
    }
    private val _steps = mutableStateListOf<String>().apply { addAll(recipe.steps) }
    private val _tools = mutableStateListOf<String>().apply { addAll(recipe.tools) }

    private val _uiState = MutableStateFlow(
        EditState(
            recipeId = recipe.id,
            name = recipe.name,
            ingredients = _ingredients,
            steps = _steps,
            tools = _tools,
            serving = recipe.serving,
            timeToMake = recipe.timeToMake.copy(),
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onServingChange(newServing: String) {
        _uiState.update { it.copy(serving = newServing) }
    }

    fun onDurationChange(newDuration: Duration) {
        _uiState.update { it.copy(timeToMake = newDuration) }
    }

    fun save(onFinished: () -> Unit) {
        val state = _uiState.value
        recipe.name = state.name
        recipe.serving = state.serving
        recipe.timeToMake = state.timeToMake

        recipe.ingredients.clear()
        recipe.ingredients.addAll(state.ingredients)

        recipe.steps.clear()
        recipe.steps.addAll(state.steps)

        recipe.tools.clear()
        recipe.tools.addAll(state.tools)

        screenModelScope.launch {
            recipeUseCase.saveToDatabase(recipe)
            onFinished()
        }
    }
}