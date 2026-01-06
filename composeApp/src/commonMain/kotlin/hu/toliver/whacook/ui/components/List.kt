package hu.toliver.whacook.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditableList(
    items: SnapshotStateList<String>,
    placeholderText: String = "add",
    buttonText: String = "Add new one"
) {
    val focusRequesters = remember { mutableStateListOf<FocusRequester>() }

    if (focusRequesters.isEmpty() && items.isNotEmpty()) {
        items.forEach { _ -> focusRequesters.add(FocusRequester()) }
    } else if (focusRequesters.isEmpty() && items.isEmpty()) {
        items.add("")
        focusRequesters.add(FocusRequester())
    }

    LaunchedEffect(items.size) {
        if (items.size > focusRequesters.size) {
            val newRequesters = items.size - focusRequesters.size
            repeat(newRequesters) {
                focusRequesters.add(FocusRequester())
            }
        }
        if (items.size > 1) {
            focusRequesters.last().requestFocus()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEachIndexed { index, item ->
            OutlinedTextField(
                value = item,
                onValueChange = { items[index] = it },
                placeholder = { if (index == 0) Text(placeholderText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequesters[index])
                    .onPreviewKeyEvent {
                        if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                            items.add(index + 1, "")
                            focusRequesters.add(index + 1, FocusRequester())
                            true
                        } else {
                            false
                        }
                    },
            )
        }

        PButton(
            buttonText,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            items.add("")
            focusRequesters.add(FocusRequester())
        }
    }
}

@Preview
@Composable
fun EditableListPreview() {
    val items = remember { mutableStateListOf("first item", "second item") }
    EditableList(items = items)
}