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

class GeminiRemoteDataSource (
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    suspend fun generate(prompt: String, context: Map<String, Any>): String {
        val response: GeminiResponse = httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "contents" to listOf(
                        mapOf(
                            "role" to "user",
                            "parts" to listOf(
                                mapOf("text" to prompt)
                            )
                        )
                    )
                )
            )
        }.body()

        return response.text
    }
}