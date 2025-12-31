package hu.toliver.whacook.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryButton: Color,
    val onPrimaryButton: Color,
    val primaryText: Color,

    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val stroke: Color,

    val background: Color,
    val onBackground: Color,

    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color
)

val LightColors = AppColors(
    primary = Color(0xFF075800),
    onPrimary = Color(0xFFFFFFFF),
    primaryButton = Color(0xFF075800),
    onPrimaryButton = Color(0xFFFFFFFF),
    primaryText = Color(0xFF000000),

    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    stroke = Color(0xFF000000),

    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF1C1B1F),

    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    success = Color(0xFF006E1C),
    onSuccess = Color(0xFFFFFFFF)
)