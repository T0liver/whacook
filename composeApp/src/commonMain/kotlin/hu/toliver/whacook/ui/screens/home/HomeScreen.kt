package hu.toliver.whacook.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import hu.toliver.whacook.ui.components.typography.Header

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeScreenViewModel>()
        HomeScreenContent(
            state = viewModel.state,
            viewModel = viewModel
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    viewModel: HomeScreenViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Header()
        Spacer(Modifier.height(16.dp))
        PButton("Add Recipe")
    }
}
