package hu.toliver.whacook.data.mapper

import hu.toliver.whacook.data.local.entity.RecipeEntity
import hu.toliver.whacook.domain.model.Recipe

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        name = name,
        ingredients = ingredients,
        steps = steps,
        tools = tools,
        serving = serving,
        favourite = favourite,
        category = category,
        timeToMake = timeToMake,
        generationTime = generationTime,
        rating = rating
    )
}

fun RecipeEntity.toDomain(): Recipe {
    return Recipe(
        id = id,
        name = name,
        ingredients = ingredients.toMutableList(),
        steps = steps.toMutableList(),
        tools = tools.toMutableList(),
        serving = serving,
        favourite = favourite,
        category = category,
        timeToMake = timeToMake,
        generationTime = generationTime,
        rating = rating
    )
}