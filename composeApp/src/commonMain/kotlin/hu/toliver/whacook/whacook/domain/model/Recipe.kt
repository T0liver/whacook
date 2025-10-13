package hu.toliver.whacook.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe (
    var id: String,
    var name: String,
    var ingredients: MutableList<Ingredient>,
    var steps: MutableList<String>,
    var tools: MutableList<String>,
    var serving: String,
    var favourite: Boolean = false,
    var category: String = "",
    var timeToMake: Duration,
    var generationTime: String = "",
    var rating: Int,
) {
    init {
        if (rating !in 0..5) {
            throw IllegalArgumentException("Rating must be between 0 and 5")
        }
    }

    override fun toString() = name
}