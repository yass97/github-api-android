package com.example.githubapi.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Cyan500 = Color(0xFF00BCD4)
val Cyan700 = Color(0xFF0097A7)
val Pink200 = Color(0xFFF48FB1)

val ListBackground = Color.LightGray.copy(alpha = 0.2f)

val DarkColorPalette = darkColors(
    primary = Cyan500,
    primaryVariant = Cyan700,
    secondary = Pink200,
)

val LightColorPalette = lightColors(
    primary = Cyan500,
    primaryVariant = Cyan700,
    secondary = Pink200,
    background = ListBackground,
)
