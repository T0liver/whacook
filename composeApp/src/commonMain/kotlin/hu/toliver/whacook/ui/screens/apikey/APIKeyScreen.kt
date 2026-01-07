package hu.toliver.whacook.ui.screens.apikey

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import hu.toliver.whacook.ui.components.BackButton
import hu.toliver.whacook.ui.components.BodyText
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.PButton
import hu.toliver.whacook.ui.components.TextBox

class APIKeyScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<APIKeyViewModel>()
        APIKeyScreenContent(
            state = viewModel.state,
            onApiKeyChange = viewModel::updateApiKey,
            onSaveClick = viewModel::saveApiKey
        )
    }
}

@Composable
fun APIKeyScreenContent(
    state: APIKeyState,
    onApiKeyChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton()
            Spacer(modifier = Modifier.width(8.dp))
            Header(text = "Your API Key")
        }

        Spacer(modifier = Modifier.height(24.dp))

        BodyText(
            text = "In order to use this application you have to add an API key for the AI you want to use."
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextBox(
            value = state.apiKey,
            placeholder = "paste API key here!",
            onValueChange = onApiKeyChange
        )

        Spacer(modifier = Modifier.height(32.dp))

        PButton(
            text = "Save",
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }
}
