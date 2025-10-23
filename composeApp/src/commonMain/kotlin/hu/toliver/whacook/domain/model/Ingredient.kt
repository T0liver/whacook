package hu.toliver.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    var name: String,
    var unit: String,
    var amount: Double,
) {
    init {
        require (amount > 0.0) { "Amount must be greater than zero." }
    }

    override fun toString() = "$name: $amount $unit"
}