package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.domain.model.Recipe

/**
 * Use case class responsible for managing the ordered list of steps within a [Recipe].
 *
 * This class provides operations to:
 * - Add new steps at specific positions.
 * - Edit existing steps.
 * - Remove steps.
 * - Reorder steps within a recipe.
 *
 * All operations validate index bounds to ensure the steps list remains consistent.
 */
class StepsUseCase {
    /**
     * Adds a new step to the given [recipe] at the specified position [ord].
     *
     * This is the default invocation of the use case and delegates to [add].
     *
     * @param recipe The recipe to which the step will be added.
     * @param step The text content of the step.
     * @param ord The index at which the step should be inserted.
     * @throws IndexOutOfBoundsException If [ord] is outside the valid range.
     */
    operator fun invoke(recipe: Recipe, step: String, ord: Int) {
        add(recipe, step, ord)
    }

    /**
     * Inserts a step into the recipe's steps list at the specified [ord] index.
     *
     * @param recipe The recipe being modified.
     * @param step The text content of the step to insert.
     * @param ord The index position where the step should be added.
     * @throws IndexOutOfBoundsException If [ord] is not within `0..steps.size`.
     */
    fun add(recipe: Recipe, step: String, ord: Int) {
        if (ord !in 0..recipe.steps.size) {
            throw IndexOutOfBoundsException(
                "Invalid position: $ord for steps list of size ${recipe.steps.size}"
            )
        }
        recipe.steps.add(ord, step)
    }

    /**
     * Replaces the step at the given position [ord] with [newStep].
     *
     * @param recipe The recipe being modified.
     * @param ord The index of the step to be replaced.
     * @param newStep The new step text to set.
     * @throws IndexOutOfBoundsException If [ord] is not a valid index in the steps list.
     */
    fun edit(recipe: Recipe, ord: Int, newStep: String) {
        if (ord !in 0..recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.steps.size}")
        }
        recipe.steps[ord] = newStep
    }

    /**
     * Removes the step at the specified index [ord] from the recipe.
     *
     * @param recipe The recipe being modified.
     * @param ord The index of the step to remove.
     * @throws IndexOutOfBoundsException If [ord] is not within the steps list range.
     */
    fun remove(recipe: Recipe, ord: Int) {
        if (ord !in 0..recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.steps.size}")
        }
        recipe.steps.removeAt(ord)
    }

    /**
     * Moves a step from one position [ordFrom] to another [ordTo] within the recipe's steps list.
     *
     * @param recipe The recipe being modified.
     * @param ordFrom The current index of the step to move.
     * @param ordTo The target index where the step should be placed.
     * @throws IndexOutOfBoundsException If either [ordFrom] or [ordTo] are out of bounds.
     */
    fun reorder(recipe: Recipe, ordFrom: Int, ordTo: Int) {
        if (ordTo !in 0..recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ordTo")
        }
        if (ordFrom !in 0..recipe.steps.size) {
            throw IndexOutOfBoundsException("Invalid position: $ordTo")
        }
        val step = recipe.steps[ordFrom]
        recipe.steps.removeAt(ordFrom)
        val adjOrdTo = if (ordTo > ordFrom) ordTo else ordFrom
        recipe.steps.add(adjOrdTo, step)
    }
}