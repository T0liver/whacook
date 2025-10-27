package hu.toliver.whacook.domain.repository

/**
 * Repository interface for generating recipe-related resources from user input.
 *
 * Implementations encapsulate the logic required to transform a user's request
 * (for example, a list of ingredients, dietary constraints, or a free-form
 * prompt) into a generated resource such as a formatted recipe, shopping list,
 * or step-by-step instructions. Generation may be asynchronous and involve I/O
 * or network operations, so callers should handle suspension, cancellations,
 * and potential failures.
 *
 * Responsibilities:
 * - Validate and normalize the provided user input.
 * - Surface errors using documented exceptions or result wrappers.
 *
 * Threading and cancellation:
 * Implementations are expected to be safe to call from coroutines. They should
 * cooperate with coroutine cancellation (throw [kotlinx.coroutines.CancellationException]
 * when cancelled) and avoid blocking threads for long-running operations.
 */
interface IRecipeGenerationRepository {
    /**
     * Generate a resource (for example, a recipe) from the provided user input.
     *
     * General expectations:
     * - `userInput` should be a concise description of what the user wants to
     *   generate (ingredients, constraints, target dish, servings, style, etc.).
     * - Implementations may accept different syntaxes (free-form text or simple
     *   key/value pairs). When a strict format is required by a concrete
     *   implementation, it should document that format separately.
     */
    suspend fun generateResource(userInput: String): String
}