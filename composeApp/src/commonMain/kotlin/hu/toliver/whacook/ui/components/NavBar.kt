package hu.toliver.whacook.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.edit
import whacook.composeapp.generated.resources.home
import whacook.composeapp.generated.resources.squaredmenu

enum class NavBarTab {
    MENU,
    HOME,
    NEW,
    NONE
}

@Composable
fun NavBar(
    selectedTab: NavBarTab,
    onMenuClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    if (selectedTab == NavBarTab.NONE) return
    
    val color = LightColors
    Box(
        modifier = Modifier
            .responsiveWidth(350.dp)
            .height(80.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp))
            .background(color.surface)
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = maxWidth
            val itemWidth = width / 3
            val indicatorOffset by animateDpAsState(
                targetValue = when (selectedTab) {
                    NavBarTab.MENU -> 0.dp
                    NavBarTab.HOME -> itemWidth
                    NavBarTab.NEW -> itemWidth * 2
                    NavBarTab.NONE -> itemWidth
                }
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(itemWidth)
                    .fillMaxHeight()
                    .padding(12.dp)
                    .background(color.secondaryButton, CircleShape)
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onMenuClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.squaredmenu),
                        contentDescription = "Menu",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onHomeClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.home),
                        contentDescription = "Home",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onEditClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}