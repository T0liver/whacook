package hu.toliver.whacook.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse (
    val id: String,
    val candidates: List<Candidate>
) {
    val text: String get() = candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text.orEmpty()

    @Serializable
    data class Candidate(val content: Content?)

    @Serializable
    data class Content(val parts: List<Part>?)

    @Serializable
    data class Part(val text: String?)
}