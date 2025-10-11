package hu.toliver.whacook.whacook.domain.usecase

import hu.toliver.whacook.whacook.domain.model.Recipe
import hu.toliver.whacook.whacook.domain.model.Ingredient

/**
 * Adds ingredient (Ingredient) from the ingredients List.
 */
class AddIngredientUseCase {
    operator fun invoke(recipe: Recipe, ingredient: Ingredient) {
        recipe.ingredients += ingredient
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

/**
 * This function removes a tool (String) to the recipe's tools list.
 */
class RemoveToolUseCase {
    operator fun invoke(recipe: Recipe, tool: String) {
        recipe.tools -= tool
    }
}