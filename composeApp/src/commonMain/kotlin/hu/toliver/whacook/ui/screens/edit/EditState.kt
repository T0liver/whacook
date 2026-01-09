package hu.toliver.whacook.ui.screens.edit

import androidx.compose.runtime.snapshots.SnapshotStateList
import hu.toliver.whacook.domain.model.Duration
import hu.toliver.whacook.domain.model.Ingredient

data class EditState(
    val recipeId: String,
    val name: String,
    val ingredients: SnapshotStateList<Ingredient>,
    val steps: SnapshotStateList<String>,
    val tools: SnapshotStateList<String>,
    val serving: String,
    val timeToMake: Duration,
)
