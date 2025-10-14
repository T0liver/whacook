package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.domain.model.Recipe

/**
 * A use case class that provides operations for managing tools associated with a recipe.
 *
 * This class encapsulates the logic for adding, editing, and removing tools within
 * a recipe, ensuring that all modifications to the recipe's tools list are performed
 * through consistent and controlled methods.
 */
class ToolsUseCase {
    /**
     * Adds a tool to the given recipe.
     *
     * This is the default invoke operation for the class, allowing it to be called
     * like a function, e.g. `toolsUseCase(recipe, "Spoon")`.
     *
     * @param recipe The recipe to which the tool will be added.
     * @param tool The tool to add to the recipe.
     */
    operator fun invoke (recipe: Recipe, tool: String) {
        add(recipe, tool)
    }

    /**
     * Adds a tool to the recipe's tools list.
     *
     * @param recipe The recipe whose tools list will be modified.
     * @param tool The tool to add to the list.
     */
    fun add(recipe: Recipe, tool: String) {
        recipe.tools += tool
    }

    /**
     * Replaces an existing tool in the recipe's tools list at the specified position.
     *
     * @param recipe The recipe containing the tool to edit.
     * @param ord The index position of the tool to replace.
     * @param newTool The new tool that will replace the existing one.
     *
     * @throws IndexOutOfBoundsException If the provided index is outside the valid range.
     */
    fun edit(recipe: Recipe, ord: Int, newTool: String) {
        if (ord < 0 || ord > recipe.tools.size) {
            throw IndexOutOfBoundsException("Invalid position: $ord for steps list of size ${recipe.tools.size}")
        }
        recipe.tools[ord] = newTool
    }

    /**
     * Removes a specific tool from the recipe's tools list.
     *
     * @param recipe The recipe whose tools list will be modified.
     * @param tool The tool to remove from the list.
     */
    fun remove(recipe: Recipe, tool: String) {
        recipe.tools -= tool
    }
}