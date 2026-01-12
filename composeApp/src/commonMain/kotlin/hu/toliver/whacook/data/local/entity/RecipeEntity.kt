package hu.toliver.whacook.data.local.entity

import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient

data class RecipeEntity(
    val id: String,
    val name: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val tools: List<String>,
    val serving: String,
    val favourite: Boolean,
    val category: String,
    val timeToMake: Duration,
    val generationTime: String,
    val rating: Int
)