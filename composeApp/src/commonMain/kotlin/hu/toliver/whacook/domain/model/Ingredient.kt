package hu.toliver.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    var name: String,
    var unit: String,
    var amount: Double,
) {

    override fun toString() = "$name: $amount $unit"
}