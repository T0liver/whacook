package hu.toliver.whacook.whacook.domain.usecase

import hu.toliver.whacook.whacook.domain.model.Recipe
import hu.toliver.whacook.whacook.domain.model.Ingredient

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

/**
 * Adds ingredient (Ingredient) from the ingredients List.
 */
class AddIngredientUseCase {
    operator fun invoke(recipe: Recipe, ingredient: Ingredient) {
        recipe.ingredients += ingredient
    }
}

/***
 * Replaces an existing ingredient at a specified position with a new one.
 */
class EditIngredientUseCase {
    operator fun invoke(recipe: Recipe, ord: Int, newIngredient: Ingredient) {
        if (ord < 0 || ord > recipe.ingredients.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.ingredients.size}")
        }
        recipe.ingredients[ord] = newIngredient
    }
}

/**
 * Removes ingredient (Ingredient) from the ingredients List.
 */
class RemoveIngredientUseCase {
    operator fun invoke(recipe: Recipe, ingredient: Ingredient) {
        recipe.ingredients -= ingredient
    }
}

/**
 * Adds a step (String) at the specified position in the recipe's steps list.
 */
class AddStepUseCase {
    operator fun invoke(recipe: Recipe, step: String, ord: Int) {
        if (ord < 0 || ord > recipe.steps.size) {
            throw IndexOutOfBoundsException(
                "Invalid position: $ord for steps list of size ${recipe.steps.size}"
            )
        }
        recipe.steps.add(ord, step)
    }
}

/***
 * Replaces an existing step at a specified position with a new one.
 */
class EditStepUseCase {
    operator fun invoke(recipe: Recipe, ord: Int, newStep: String) {
        if (ord < 0 || ord > recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.steps.size}")
        }
        recipe.steps[ord] = newStep
    }
}

/***
 * Removes a step (String) from the specified position in the recipe's steps list.
 */
class RemoveStepUseCase {
    operator fun invoke(recipe: Recipe, ord: Int) {
        if (ord < 0 || ord >= recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.steps.size}")
        }
        recipe.steps.removeAt(ord)
    }
}

/**
 * Reorders the steps in the recipe by moving a step from ordFrom to ordTo.
 */
class ReorderStepUseCase {
    operator fun invoke(recipe: Recipe, ordFrom: Int, ordTo: Int) {
        if (ordTo !in 0 until recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ordTo")
        }
        if (ordFrom !in 0 until recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ordTo")
        }
        val step = recipe.steps[ordFrom]
        recipe.steps.removeAt(ordFrom)
        val adjOrdTo = if (ordTo > ordFrom) ordTo else ordFrom
        recipe.steps.add(adjOrdTo, step)
    }
}

/**
 * This function adds a tool (String) to the recipe's tools list.
 */
class AddToolUseCase {
    operator fun invoke(recipe: Recipe, tool: String) {
        recipe.tools += tool
    }
}

/***
 * Replaces an existing tool at a specified position with a new one.
 */
class EditToolUseCase {
    operator fun invoke(recipe: Recipe, ord: Int, newTool: String) {
        if (ord < 0 || ord > recipe.tools.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.tools.size}")
        }
        recipe.tools[ord] = newTool
    }
}

/**
 * This function removes a tool (String) to the recipe's tools list.
 */
class RemoveToolUseCase {
    operator fun invoke(recipe: Recipe, tool: String) {
        recipe.tools -= tool
    }
}

/***
 * Rates the recepie in scale 0-5.
 */
class RateRecipeUseCase {
    operator fun invoke(recipe: Recipe, rating: Int) {
        if (rating !in 0..5) {
            throw IllegalArgumentException("Rating must be between 0 and 5")
        }
        recipe.rating = rating
    }
}

/***
 * Saves the recepie into a json formatted string
 */
class SaveRecipeUseCase {
    operator fun invoke(recipe: Recipe): String {
        val json = Json.encodeToString(recipe)
        println(json)
        return json
    }
}

/***
 * Loads recipe from a json formatted string to a recipe object
 */
class LoadRecipeUseCase {
    operator fun invoke(json: String): Recipe {
        return  Json.decodeFromString<Recipe>(json)
    }
}