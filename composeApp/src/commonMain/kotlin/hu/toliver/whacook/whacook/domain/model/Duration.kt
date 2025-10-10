package hu.toliver.whacook.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Duration (
    var length: Double,
    var unit: String,
) {
    override fun toString() = "$length $unit"
}