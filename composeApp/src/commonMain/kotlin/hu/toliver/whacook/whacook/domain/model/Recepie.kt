package hu.toliver.whacook.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Recepie (
    var id: String,
    var name: String,
    var ingredients: List<Ingredient>,
    var steps: List<String>,
    var tools: List<String>,
    var serving: String,
    var favorite: Boolean = false,
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