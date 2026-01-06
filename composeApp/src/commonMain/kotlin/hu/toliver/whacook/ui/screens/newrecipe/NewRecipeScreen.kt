package hu.toliver.whacook.ui.screens.newrecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import hu.toliver.whacook.ui.components.BodyTextSmall
import hu.toliver.whacook.ui.components.EditableList
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.PButton

class NewRecipeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<NewRecipeScreenViewModel>()
        NewRecipeScreenContent(
            state = viewModel.state,
            viewModel = viewModel
        )
    }
}

@Composable
private fun NewRecipeScreenContent(
    @Suppress("UNUSED_PARAMETER")
    state: NewRecipeState,
    @Suppress("UNUSED_PARAMETER")
    viewModel: NewRecipeScreenViewModel
) {
    val ingredients = remember { mutableStateListOf<String>() }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val screenHeight = maxHeight
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = screenHeight),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Header("New Recipe")
                    Spacer(Modifier.height(16.dp))
                    EditableList(ingredients)
                    PButton("Generate Recipe")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BodyTextSmall("Type in one by one what ingredients you have at home and then a LLM will give you a recipe advice based on the list you give what to cook with units and preparation steps.")
                    Spacer(Modifier.height(120.dp))
                }
            }
        }
    }
}
