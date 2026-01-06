package hu.toliver.whacook.data.di

import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.screens.home.HomeScreenViewModel
import hu.toliver.whacook.ui.screens.newrecipe.NewRecipeScreenViewModel
import hu.toliver.whacook.ui.screens.recipe.RecipeScreenViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { HomeScreenViewModel(get(), get()) }
    factory { NewRecipeScreenViewModel() }

    factory { (recipe: Recipe) ->
        RecipeScreenViewModel(recipe = recipe)
    }

}