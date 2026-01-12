package hu.toliver.whacook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hu.toliver.whacook.ui.theme.LightColors
import org.jetbrains.compose.resources.painterResource
import whacook.composeapp.generated.resources.Res
import whacook.composeapp.generated.resources.back
import whacook.composeapp.generated.resources.filledheart
import whacook.composeapp.generated.resources.filledstar
import whacook.composeapp.generated.resources.heart
import whacook.composeapp.generated.resources.star

@Composable
fun PButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    backgroundColor: Color = LightColors.primaryButton,
    textColor: Color = LightColors.onPrimaryButton,
    onClick: () -> Unit = {},
) {
    val colors = LightColors
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
        ),
        border = BorderStroke(2.dp, colors.stroke),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text)
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit = { }
) {
    val navigator = LocalNavigator.currentOrThrow
    IconButton(
        onClick = {
            onClick()
            navigator.pop()
        }
    ) {
        Icon(
            painter = painterResource(Res.drawable.back),
            contentDescription = "Back"
        )
    }
}

@Composable
fun FavouriteButton(
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp,
    onClick: () -> Unit = {},
) {
    val iconRes = if (isFavourite) {
        Res.drawable.filledheart
    } else {
        Res.drawable.heart
    }
    Box(
        modifier = modifier
            .size(iconSize)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = "Favourite",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RatingStars(
    rating: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp,
    onRatingChanged: (Int) -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        for (i in 1..5) {
            val starRes = if (i <= rating) {
                Res.drawable.filledstar
            } else {
                Res.drawable.star
            }
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(CircleShape)
                    .clickable { onRatingChanged(i) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(starRes),
                    contentDescription = "Star $i",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}