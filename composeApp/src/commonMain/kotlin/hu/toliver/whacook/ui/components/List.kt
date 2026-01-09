package hu.toliver.whacook.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.trashcan

@Composable
fun EditableList(
    items: SnapshotStateList<String>,
    placeholderText: String = "add"
) {
    val focusRequesters = remember { mutableStateListOf<FocusRequester>() }
    val shouldFocusLastItem = remember { mutableStateOf(false) }

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
        if (shouldFocusLastItem.value) {
            focusRequesters.last().requestFocus()
            shouldFocusLastItem.value = false
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEachIndexed { index, item ->
            OutlinedTextField(
                value = item,
                onValueChange = {
                    items[index] = it
                    if (index == items.lastIndex && it.isNotEmpty()) {
                        items.add("")
                        focusRequesters.add(FocusRequester())
                    }
                },
                singleLine = true,
                placeholder = { if (index == 0) Text(placeholderText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequesters[index])
                    .onPreviewKeyEvent {
                        if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                            if (index < items.lastIndex) {
                                focusRequesters[index + 1].requestFocus()
                            } else if (items[index].isNotEmpty()) {
                                items.add("")
                                focusRequesters.add(FocusRequester())
                                shouldFocusLastItem.value = true
                            }
                            true
                        } else {
                            false
                        }
                    },
            )
        }


    }
}

@Preview
@Composable
fun EditableListPreview() {
    val items = remember { mutableStateListOf("first item", "second item") }
    EditableList(items = items)
}

@Composable
fun TextBoxEditableList(
    items: SnapshotStateList<String>,
    placeholderText: String
) {
    val focusRequesters = remember { mutableStateListOf<FocusRequester>() }
    val focusIndex = remember { mutableStateOf(-1) }

    if (focusRequesters.isEmpty() && items.isNotEmpty()) {
        items.forEach { _ -> focusRequesters.add(FocusRequester()) }
    } else if (items.isEmpty()) {
        LaunchedEffect(Unit) {
            items.add("")
            focusRequesters.add(FocusRequester())
        }
    }

    LaunchedEffect(items.size) {
        while (focusRequesters.size < items.size) focusRequesters.add(FocusRequester())
        while (focusRequesters.size > items.size) focusRequesters.removeLast()
    }

    LaunchedEffect(focusIndex.value) {
        if (focusIndex.value != -1 && focusIndex.value < focusRequesters.size) {
            focusRequesters[focusIndex.value].requestFocus()
            focusIndex.value = -1
        }
    }

    Column(
        modifier = Modifier.responsiveWidth(800.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("${index + 1}.")
                OutlinedTextField(
                    value = item,
                    onValueChange = { items[index] = it },
                    placeholder = { Text(placeholderText) },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(if (index < focusRequesters.size) focusRequesters[index] else FocusRequester())
                        .onPreviewKeyEvent {
                            if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                                if (it.isShiftPressed) {
                                    false
                                } else {
                                    items.add(index + 1, "")
                                    focusRequesters.add(index + 1, FocusRequester())
                                    focusIndex.value = index + 1
                                    true
                                }
                            } else {
                                false
                            }
                        }
                )
                IconButton(onClick = {
                    items.removeAt(index)
                    focusRequesters.removeAt(index)
                    if (items.isEmpty()) {
                        items.add("")
                        focusRequesters.add(FocusRequester())
                    }
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.trashcan),
                        contentDescription = "Delete item"
                    )
                }
            }
        }

        Button(
            onClick = {
                items.add("")
                focusRequesters.add(FocusRequester())
                focusIndex.value = items.lastIndex
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add step")
        }
    }
}