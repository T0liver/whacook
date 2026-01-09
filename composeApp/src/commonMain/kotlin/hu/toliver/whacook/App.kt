package hu.toliver.whacook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import hu.toliver.whacook.data.di.initKoin
import hu.toliver.whacook.ui.components.NavBar
import hu.toliver.whacook.ui.components.NavBarTab
import hu.toliver.whacook.ui.screens.home.HomeScreen
import hu.toliver.whacook.ui.screens.menu.MenuScreen
import hu.toliver.whacook.ui.screens.newrecipe.NewRecipeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        Navigator(HomeScreen()) { navigator ->
            Box(modifier = Modifier.fillMaxSize()) {
                CurrentScreen()
                Spacer(Modifier.height(100.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp)
                ) {
                    val selectedTab = when (navigator.lastItem) {
                        is MenuScreen -> NavBarTab.MENU
                        is HomeScreen -> NavBarTab.HOME
                        is NewRecipeScreen -> NavBarTab.NEW
                        else -> NavBarTab.NONE
                    }
                    NavBar(
                        selectedTab = selectedTab,
                        onMenuClick = { navigator.replaceAll(MenuScreen()) },
                        onHomeClick = { navigator.replaceAll(HomeScreen()) },
                        onEditClick = { navigator.replaceAll(NewRecipeScreen()) },
                    )
                }
            }
        }
    }
}
