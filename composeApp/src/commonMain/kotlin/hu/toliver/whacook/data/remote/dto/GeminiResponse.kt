package hu.toliver.whacook.data.remote.dto

data class GeminiResponse (
    val id: String,
    val candidates: List<Candidate>
) {
    val text: String get() = candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text.orEmpty()

    data class Candidate(val content: Content?)

    data class Content(val parts: List<Part>?)

    data class Part(val text: String?)
}