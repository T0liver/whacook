package hu.toliver.whacook.ui.screens.recipepreferences

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.components.BackButton
import hu.toliver.whacook.ui.components.BodyText
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.PButton
import hu.toliver.whacook.ui.components.TextBox

class RecipePreferencesScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<RecipePreferencesScreenViewModel>()
        RecipePreferencesScreenContent(
            state = viewModel.state,
            viewModel = viewModel
        )
    }

}

@Composable
private fun RecipePreferencesScreenContent(
    state: RecipePreferencesState,
    viewModel: RecipePreferencesScreenViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton()
            Spacer(modifier = Modifier.width(8.dp))
            Header(text = "Recipe Preference")
        }

        Spacer(modifier = Modifier.height(24.dp))

        BodyText(
            text = "Write preferences with the recipes you generating! For example: you are not eating meat, you are lactose intolerant etc."
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextBox(
            value = state.preference,
            placeholder = "write your preferences here!",
            onValueChange = { viewModel.updatePreference(it) },
        )

        Spacer(modifier = Modifier.height(32.dp))

        PButton(
            text = "Save",
            onClick = {
                viewModel.savePreference()
                navigator.pop()
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }
}