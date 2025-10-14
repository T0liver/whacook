package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.domain.model.Ingredient
import hu.toliver.whacook.domain.model.Recipe

/**
 * A use case class responsible for managing ingredients within a [Recipe].
 *
 * Provides operations to add, edit, and remove [Ingredient] objects
 * from a recipe’s ingredient list. This class is typically used as part
 * of a domain layer to encapsulate ingredient manipulation logic.
 */
class IngredientsUseCase {
    /**
     * Adds an [ingredient] to the given [recipe].
     *
     * This operator function allows shorthand usage, e.g.:
     * ```
     * ingredientsUseCase(recipe, ingredient)
     * ```
     * which internally calls [add].
     */
    operator fun invoke (recipe: Recipe, ingredient: Ingredient) {
        add(recipe, ingredient)
    }

    /**
     * Appends the specified [ingredient] to the [recipe]'s ingredient list.
     *
     * @param recipe The recipe to modify.
     * @param ingredient The ingredient to add.
     */
    fun add(recipe: Recipe, ingredient: Ingredient) {
        recipe.ingredients += ingredient
    }

    /**
     * Replaces an existing ingredient in [recipe] at the given position [ord]
     * with a new [newIngredient].
     *
     * @param recipe The recipe to modify.
     * @param ord The position (0-based index) of the ingredient to replace.
     * @param newIngredient The new ingredient to insert.
     * @throws IndexOutOfBoundsException if [ord] is outside the valid range.
     */
    fun edit(recipe: Recipe, ord: Int, newIngredient: Ingredient) {
        if (ord < 0 || ord > recipe.ingredients.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.ingredients.size}")
        }
        recipe.ingredients[ord] = newIngredient
    }

    /**
     * Removes the specified [ingredient] from the [recipe]'s ingredient list.
     *
     * @param recipe The recipe to modify.
     * @param ingredient The ingredient to remove.
     */
    fun remove(recipe: Recipe, ingredient: Ingredient) {
        recipe.ingredients -= ingredient
    }
}