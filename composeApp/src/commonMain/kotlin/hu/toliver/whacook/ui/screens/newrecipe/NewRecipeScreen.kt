package hu.toliver.whacook.ui.screens.newrecipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    val meaningfulIngredients = ingredients.map { it.trim() }.filter { it.isNotEmpty() }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            Header("New Recipe")
            Spacer(modifier = Modifier.height(16.dp))

            EditableList(ingredients)

            BodyTextSmall("Type in one by one what ingredients you have at home and then a LLM will give you a recipe advice based on the list you give what to cook with units and preparation steps.")
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
