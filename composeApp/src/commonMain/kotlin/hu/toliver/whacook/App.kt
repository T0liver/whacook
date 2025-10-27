package hu.toliver.whacook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import hu.toliver.whacook.data.di.createRecepieGenerationUseCase

import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var generatedText by remember { mutableStateOf<String?>(null) }

        val recipeUseCase = remember { createRecepieGenerationUseCase() }

        LaunchedEffect(showContent) {
            if (showContent) {
                generatedText = try {
                    recipeUseCase.invoke("Generate a short friendly greeting for the app user. The output should be only one sentence but this should be a little longer than plain 'Hi!'")
                } catch (t: Throwable) {
                    "Error generating text: ${t.message}"
                }
            }
        }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                    Text(generatedText ?: "Generating AI text...")
                }
            }
            Text("Hello, World!")
            Text("I want to generate a greeting here with AI using the RecipeGenerationUseCase.")
        }
    }
}