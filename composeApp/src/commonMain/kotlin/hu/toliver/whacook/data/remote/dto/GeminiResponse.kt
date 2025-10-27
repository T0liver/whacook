package hu.toliver.whacook.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data transfer object representing a Gemini API response.
 *
 * Models the top-level response returned by the Gemini endpoint. The response
 * contains an identifier and a list of candidate results; each candidate may
 * carry structured content made of parts.
 *
 * @property id Unique identifier of the response.
 * @property candidates Ordered list of candidate results returned by the API.
 */
@Serializable
data class GeminiResponse(
    val id: String,
    val candidates: List<Candidate>
) {
    /**
     * Convenience getter that returns the textual content of the first available
     * candidate -> content -> part. If no text is available an empty string is returned.
     */
    val text: String
        get() = candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text.orEmpty()

    /**
     * A candidate wrapper which may contain structured content.
     *
     * @property content The structured content for this candidate, or null if absent.
     */
    @Serializable
    data class Candidate(val content: Content?)

    /**
     * Structured content container that holds message parts.
     *
     * @property parts A list of message parts, or null when missing.
     */
    @Serializable
    data class Content(val parts: List<Part>?)

    /**
     * Individual message part which may contain textual content.
     *
     * @property text The textual payload of this part, or null when absent.
     */
    @Serializable
    data class Part(val text: String?)
}