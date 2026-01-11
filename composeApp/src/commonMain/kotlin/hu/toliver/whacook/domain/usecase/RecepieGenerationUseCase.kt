package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository

/**
 * Use case responsible for generating recipe text resources using a repository.
 *
 * This class delegates the actual generation work to an implementation of
 * [IRecipeGenerationRepository]. It provides a lightweight operator function
 * for arbitrary prompt-based generation and a domain-specific helper that
 * builds a cooking-oriented system prompt from a list of ingredients.
 *
 * @property repository repository used to generate resources (e.g. via an API call)
 */
class RecepieGenerationUseCase (
    private val repository: IRecipeGenerationRepository
) {
    /**
     * Invoke the repository to generate a resource from an arbitrary prompt.
     *
     * This is a convenience wrapper so the use case can be called like a
     * function: `recepieGenerationUseCase("some prompt")`.
     *
     * @param prompt the text prompt to send to the repository
     * @return the raw string response produced by the repository
     */
    suspend operator fun invoke(prompt: String): String {
        return repository.generateResource(prompt)
    }

    /**
     * Generate a recipe using the provided list of ingredients.
     *
     * The function builds a detailed system prompt describing the exact JSON
     * structure required for the recipe, appends the given ingredients and
     * then asks the repository to generate the recipe content.
     *
     * The returned string is expected to be the raw JSON produced by the
     * underlying repository. The repository is responsible for network I/O
     * and any associated errors; callers should handle exceptions as needed.
     *
     * @param ingredients the list of ingredient names available to use in the recipe
     * @return a JSON string describing the generated recipe
     */
    suspend fun generateRecipe(ingredients: List<String>): String {
        if (ingredients.isEmpty()) throw IllegalArgumentException("Ingredient list cannot be empty")
        val sysPrompt = """
            You are a cooking assistant that creates structured recipes in JSON format.

            I will give you a list of ingredients I currently have at home.
            You will create a realistic, complete recipe that uses some or all of those ingredients.

            Your response must be valid JSON and follow this exact structure and field order:

            {
              "id": "string - unique recipe ID",
              "name": "string - the recipe name",
              "ingredients": [
                {
                  "name": "string - ingredient name",
                  "unit": "string - measurement unit (e.g., grams, cups, slices, teaspoon)",
                  "amount": number - numeric quantity
                }
              ],
              "steps": [
                "string - ordered list of steps to prepare the recipe"
              ],
              "tools": [
                "string - list of kitchen tools required"
              ],
              "serving": "string - short serving suggestion",
              "category": "string - e.g., dessert, breakfast, pasta, salad, etc.",
              "timeToMake": {
                "length": number - estimated time length,
                "unit": "string - minutes or hours"
              },
              "generationTime": "YYYY-MM-DD-HH-MM-SS - current timestamp",
              "rating": 0,
              "favourite": false
            }

            Rules:

            Always return only valid JSON — no markdown, no explanations, no extra text.

            Use realistic ingredient amounts and units.

            The recipe should be possible to cook with the provided ingredients.

            Set "rating": 0 and "favourite": false by default.

            "generationTime" should reflect the current date and time.

            The ingredients are:
        """.trimIndent()
        val userPrompt = ingredients.joinToString(",\n")

        val stringBuilder = StringBuilder()
        stringBuilder.append(sysPrompt)
        stringBuilder.append("\n")
        stringBuilder.append(userPrompt)

        val prompt = stringBuilder.toString()
        return repository.generateResource(prompt)
    }
}