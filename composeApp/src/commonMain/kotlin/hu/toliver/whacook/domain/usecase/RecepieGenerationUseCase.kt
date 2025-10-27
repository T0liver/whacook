package hu.toliver.whacook.domain.usecase

import hu.toliver.whacook.domain.repository.IRecipeGenerationRepository

class RecepieGenerationUseCase (
    private val repository: IRecipeGenerationRepository
) {
    suspend operator fun invoke(prompt: String): String {
        return repository.generateResource(prompt)
    }

    suspend fun generateRecipe(ingredients: List<String>): String {
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