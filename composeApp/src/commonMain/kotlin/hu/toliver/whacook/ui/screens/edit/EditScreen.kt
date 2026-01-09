package hu.toliver.whacook.ui.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.domain.model.Recipe
import hu.toliver.whacook.ui.components.BackButton
import hu.toliver.whacook.ui.components.DurationChooser
import hu.toliver.whacook.ui.components.EditableList
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.IngredientEditableList
import hu.toliver.whacook.ui.components.PButton
import hu.toliver.whacook.ui.components.Subheader
import hu.toliver.whacook.ui.components.TextBoxEditableList
import org.koin.core.parameter.parametersOf

data class EditScreen(val recipe: Recipe) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<EditScreenViewModel> { parametersOf(recipe) }
        val state by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 30.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton()
                Spacer(modifier = Modifier.width(8.dp))
                Header("Edit Recipe")
            }

            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Recipe Name") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            Subheader("Time to make")
            DurationChooser(
                duration = state.timeToMake,
                onValueChange = viewModel::onDurationChange
            )

            Subheader("Ingredients")
            IngredientEditableList(ingredients = state.ingredients)

            Subheader("Steps")
            TextBoxEditableList(items = state.steps, placeholderText = "Describe step...")

            Subheader("Tools")
            EditableList(items = state.tools, placeholderText = "Add tool")

            Subheader("Serving")
            OutlinedTextField(
                value = state.serving,
                onValueChange = viewModel::onServingChange,
                label = { Text("Serving") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(16.dp))

            PButton(
                text = "Save",
                onClick = {
                    viewModel.save()
                    navigator.pop()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(120.dp))
        }
    }
}