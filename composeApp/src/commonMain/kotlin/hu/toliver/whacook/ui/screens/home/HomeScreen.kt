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
import hu.toliver.whacook.ui.components.BodyTextUnderline
import hu.toliver.whacook.ui.components.PButton
import hu.toliver.whacook.ui.components.TypeBar
import hu.toliver.whacook.ui.components.Header
import hu.toliver.whacook.ui.components.RecipeCard
import hu.toliver.whacook.ui.components.SearchCard
import hu.toliver.whacook.ui.components.Subheader

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
        Spacer(Modifier.height(8.dp))
        SearchCard()
        Spacer(Modifier.height(16.dp))
        Subheader("Recent recipes")
        Spacer(Modifier.height(8.dp))
        Column(Modifier.fillMaxWidth(0.85f)) {
            RecipeCard()
        }
    }
}
