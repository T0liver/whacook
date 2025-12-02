package hu.toliver.whacook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Separator(
    color: Long = 0xFF6C0000,
    width: Dp = 90.dp,
    thickness: Dp = 2.dp,
) {
    Box(
        modifier = Modifier.width(width),
    ) {
        HorizontalDivider(
            thickness = thickness,
            color = Color(color)
        )
    }
}