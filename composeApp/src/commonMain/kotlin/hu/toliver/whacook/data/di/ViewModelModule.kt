package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.screens.apikey.APIKeyViewModel
import hu.toliver.whacook.ui.screens.edit.EditScreenViewModel
import hu.toliver.whacook.ui.screens.home.HomeScreenViewModel
import hu.toliver.whacook.ui.screens.menu.MenuScreenViewModel
import hu.toliver.whacook.ui.screens.newrecipe.NewRecipeScreenViewModel
import hu.toliver.whacook.ui.screens.recipe.RecipeScreenViewModel
import hu.toliver.whacook.ui.screens.recipepreferences.RecipePreferencesScreenViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { APIKeyViewModel(get()) }
    factory { HomeScreenViewModel(get(), get(), get()) }
    factory { MenuScreenViewModel() }
    factory { NewRecipeScreenViewModel(get(), get(), get()) }
    factory { RecipePreferencesScreenViewModel() }

    factory { (recipe: Recipe) ->
        RecipeScreenViewModel(recipe = recipe, recipeUseCase = get(), recipeRepository = get())
    }

    factory { (recipe: Recipe) ->
        EditScreenViewModel(recipe = recipe)
    }

}