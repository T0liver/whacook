package hu.toliver.whacook.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditableList() {
    val items = remember { mutableStateListOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items) { index, item ->
            OutlinedTextField(
                value = item,
                onValueChange = { items[index] = it },
                placeholder = { if (index == 0) Text("add") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )
        }

        item {
            Button(
                onClick = { items.add("") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Add new one")
            }
        }
    }
}

@Preview
@Composable
fun EditableListPreview() {
    EditableList()
}