package hu.toliver.whacook

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import hu.toliver.whacook.ui.components.EditableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val ingredients = mutableStateListOf<String>()
    MaterialTheme {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EditableList(ingredients)
            Button(onClick = {
                print(ingredients.joinToString(", "))
            }) {
                Text("Print")
            }
        }
    }
}