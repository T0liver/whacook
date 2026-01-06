package hu.toliver.whacook.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.edit
import whacook.composeapp.generated.resources.fryingpan
import whacook.composeapp.generated.resources.home
import whacook.composeapp.generated.resources.squaredmenu

fun Modifier.responsiveWidth(maxWidth: Dp): Modifier = this
    .fillMaxWidth()
    .wrapContentWidth(Alignment.CenterHorizontally)
    .layout { measurable, constraints ->
    val maxPx = maxWidth.roundToPx()
    val fractionPx = if (constraints.hasBoundedWidth) (constraints.maxWidth * 0.85f).toInt() else maxPx
    val targetWidth = fractionPx.coerceAtMost(maxPx)
    val finalWidth = targetWidth.coerceIn(constraints.minWidth, constraints.maxWidth)

    val placeable = measurable.measure(
        constraints.copy(
            minWidth = finalWidth,
            maxWidth = finalWidth
        )
    )
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}

@Composable
fun TypeBar(
    text: String,
    icon: DrawableResource = Res.drawable.fryingpan,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .clip(CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = LightColors.primaryText
        )
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onDescribeClick: () -> Unit = {}
) {
    val color = LightColors
    Card(
        modifier = modifier
            .responsiveWidth(800.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = color.primary,
                ambientColor = color.primary
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "What are we cooking today?",
                fontSize = 22.sp,
                color = color.primaryText,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            TypeBar(
                text = "Type in your ingredients!",
                onClick = onSearchClick
            )

            BodyTextUnderline(
                text = "...or just describe your food!",
                onClick = onDescribeClick
            )
        }
    }
}

@Composable
fun RecipeCard(
    title: String,
    time: String,
    ingredientsCount: Int,
    date: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val color = LightColors
    Card(
        modifier = modifier
            .responsiveWidth(800.dp)
            .border(1.dp, color.secondaryStroke, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BodyTextHeader(
                title,
                color = color.primaryText,
            )
            BodyText(
                "Time to make: $time",
                color = color.primaryText
            )
            BodyText(
                "Num of ingredients: $ingredientsCount things",
                color = color.primaryText
            )
            BodyTextSmall(
                date,
                color = color.secondaryText
            )
        }
    }
}

@Composable
fun PopUp(
    headerText: String,
    bodyText: String,
    buttonText: String,
    onConfirm: () -> Unit = {}
) {
    val color = LightColors
    Card(
        modifier = Modifier
            .responsiveWidth(400.dp)
            .border(3.dp, color.stroke, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(headerText)

            Spacer(modifier = Modifier.height(16.dp))

            BodyText(
                text = bodyText,
                fontSize = 18.sp,
                color = color.primaryText
            )

            Spacer(modifier = Modifier.height(32.dp))

            PButton(
                text = buttonText,
                onClick = onConfirm
            )
        }
    }
}

@Composable
fun PopUpOverlay(
    headerText: String,
    bodyText: String,
    buttonText: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {}
        ) {
            PopUp(
                headerText = headerText,
                bodyText = bodyText,
                buttonText = buttonText,
                onConfirm = {
                    onConfirm()
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun NavBar(
    onMenuClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
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
            var selectedTab by remember { mutableStateOf(1) }
            
            val width = maxWidth
            val itemWidth = width / 3
            val indicatorOffset by animateDpAsState(
                targetValue = when (selectedTab) {
                    0 -> 0.dp
                    1 -> itemWidth
                    2 -> itemWidth * 2
                    else -> itemWidth
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
                        ) { selectedTab = 0; onMenuClick() },
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
                        ) { selectedTab = 1; onHomeClick() },
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
                        ) { selectedTab = 2; onEditClick() },
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