package hu.toliver.whacook.data.remote

import hu.toliver.whacook.data.local.PreferencesManager
import hu.toliver.whacook.data.remote.dto.GeminiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive

/**
 * Remote data source for interacting with Google's Gemini generative language API.
 *
 * Uses a provided [HttpClient] to POST a minimal request and returns the generated text.
 * The API key is retrieved from [preferencesManager] and may be either a Google API key
 * (starts with "AIza") or an OAuth bearer token. If an API key is detected it is appended
 * as a query parameter; otherwise the token is sent in the Authorization header as a Bearer token.
 *
 * @property httpClient configured Ktor HttpClient used to perform requests
 * @property preferencesManager manager to retrieve the stored API key
 */
class GeminiRemoteDataSource (
    private val httpClient: HttpClient,
    private val preferencesManager: PreferencesManager
) {
    /**
     * Sends [prompt] to the Gemini model and returns generated text.
     *
     * The [context] parameter is reserved for future use and is not serialized in the current implementation.
     * This function:
     * 1. Builds a small JSON payload containing the prompt.
     * 2. Retrieves the API Key from preferences.
     * 3. Chooses how to supply the key (query param for API keys, Authorization header for bearer tokens).
     * 4. Attempts to decode the response into [GeminiResponse]. If decoding fails it will try to extract the
     *    first textual field from the JSON response. If extraction also fails the raw response string is returned.
     *
     * @param prompt the prompt text to send to the model
     * @param context optional contextual data (unused)
     * @return generated text from the model or the raw response if parsing/extraction fails
     * @throws Exception propagated from the underlying [HttpClient] on network errors
     */
    suspend fun generate(prompt: String): String {
        val storedKey = preferencesManager.apiKey

        if (storedKey.isNullOrEmpty()) {
            throw IllegalStateException("API Key not found. Please add it in settings.")
        }

        val requestBody = buildJsonObject {
            putJsonArray("contents") {
                add(buildJsonObject {
                    put("role", "user")
                    putJsonArray("parts") {
                        add(buildJsonObject { put("text", prompt) })
                    }
                })
            }
        }

        val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"

        val call = when {
            storedKey.startsWith("AIza") -> {
                httpClient.post("${baseUrl}?key=$storedKey") {
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)
                }
            }
            else -> {
                httpClient.post(baseUrl) {
                    header(HttpHeaders.Authorization, "Bearer $storedKey")
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)
                }
            }
        }

        val rawResponse: String = call.body()

        val jsonParser = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        val parsedDto = runCatching {
            jsonParser.decodeFromString(GeminiResponse.serializer(), rawResponse)
        }.getOrNull()

        if (parsedDto != null) return parsedDto.text

        val jsonElement = runCatching { jsonParser.parseToJsonElement(rawResponse) }.getOrNull()
        if (jsonElement != null) {
            extractFirstText(jsonElement)?.let { return it }
        }

        return rawResponse
    }

    /**
     * Recursively searches [element] for the first string value typically used to carry model output.
     *
     * The search looks for a direct "text" field, a "candidates" array (it inspects the first candidate),
     * and otherwise descends into nested objects and arrays to find the first string primitive.
     *
     * @param element the JSON element to inspect
     * @return the first textual value found or null when no string is present
     */
    private fun extractFirstText(element: JsonElement): String? {
        when (element) {
            is JsonObject -> {
                element["text"]?.let { if (it is JsonPrimitive && it.isString) return it.content }
                element["candidates"]?.let { it ->
                    if (it is JsonArray && it.isNotEmpty()) {
                        extractFirstText(it[0])?.let { return it }
                    }
                }
                for ((_, v) in element) {
                    extractFirstText(v)?.let { return it }
                }
            }
            is JsonArray -> {
                for (item in element) {
                    extractFirstText(item)?.let { return it }
                }
            }
            is JsonPrimitive -> {
                if (element.isString) return element.content
            }
        }
        return null
    }
}