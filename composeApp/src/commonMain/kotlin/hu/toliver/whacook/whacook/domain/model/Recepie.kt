package hu.toliver.whacook.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Recepie (
    val id: String,
    var name: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val tools: List<String>,
    val serving: String,
    val favorite: Boolean = false,
    val category: String = "",
    var timeToMake: Duration,
    val generationTime: String = "",
    val rating: Int,
) {
    init {
        if (rating !in 0..5) {
            throw IllegalArgumentException("Rating must be between 0 and 5")
        }
    }

    override fun toString() = name
}