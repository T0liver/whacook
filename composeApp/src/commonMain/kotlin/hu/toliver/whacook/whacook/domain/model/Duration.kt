package hu.toliver.whacook.whacook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Duration (
    val length: Double,
    val unit: String,
) {
    override fun toString() = "$length $unit"
}