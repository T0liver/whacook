package hu.toliver.whacook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.fryingpan

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
            .layout { measurable, constraints ->
                val maxPx = 800.dp.roundToPx()
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
            .fillMaxWidth()
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
