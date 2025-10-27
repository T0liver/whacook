package hu.toliver.whacook.data.remote

import hu.toliver.whacook.data.remote.dto.GeminiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive

class GeminiRemoteDataSource (
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    suspend fun generate(prompt: String, context: Map<String, Any>): String {
        // Build the request body as a JsonObject to avoid serializing arbitrary Maps
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

        val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent"

        val call = when {
            // Next, if the provided apiKey looks like an API key (starts with AIza), pass it as query param
            apiKey.startsWith("AIza") -> {
                httpClient.post("${baseUrl}?key=$apiKey") {
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)
                }
            }
            // Otherwise treat the provided apiKey as a bearer token (user-supplied OAuth token)
            else -> {
                httpClient.post(baseUrl) {
                    header(HttpHeaders.Authorization, "Bearer $apiKey")
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)
                }
            }
        }

        val rawResponse: String = call.body()

        // Use a lenient Json parser with ignoreUnknownKeys so decoding is forgiving
        val jsonParser = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        // Try to decode into the expected DTO first
        val parsedDto = runCatching {
            jsonParser.decodeFromString(GeminiResponse.serializer(), rawResponse)
        }.getOrNull()

        if (parsedDto != null) return parsedDto.text

        // If decoding failed, try to find the first "text" field in the JSON response
        val jsonElement = runCatching { jsonParser.parseToJsonElement(rawResponse) }.getOrNull()
        if (jsonElement != null) {
            extractFirstText(jsonElement)?.let { return it }
        }

        // Fall back to raw response string so the caller sees something useful
        return rawResponse
    }

    private fun extractFirstText(element: JsonElement): String? {
        when (element) {
            is JsonObject -> {
                // direct text field
                element["text"]?.let { if (it is JsonPrimitive && it.isString) return it.content }
                // typical candidate path
                element["candidates"]?.let {
                    if (it is JsonArray && it.isNotEmpty()) {
                        extractFirstText(it[0])?.let { return it }
                    }
                }
                // recurse into children
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