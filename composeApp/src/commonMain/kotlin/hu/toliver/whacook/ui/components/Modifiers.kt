package hu.toliver.whacook.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

fun Modifier.responsiveWidth(
    maxWidth: Dp,
    fraction: Float = 0.85f
): Modifier = this
    .fillMaxWidth()
    .wrapContentWidth(Alignment.CenterHorizontally)
    .layout { measurable, constraints ->
        val maxPx = maxWidth.roundToPx()
        val fractionPx = if (constraints.hasBoundedWidth) (constraints.maxWidth * fraction).toInt() else maxPx
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