package hu.toliver.whacook.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    val name: String,
    val unit: String,
    val amount: Double,
) {
    init {
        if (amount <= 0.0) {
            throw IllegalArgumentException("Amount must be greater than zero.")
        }
    }

    override fun toString() = "$name: $amount $unit"
}