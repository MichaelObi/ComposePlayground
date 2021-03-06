package dev.michaelobi.clubhouse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primary = Color.Black,
    secondary = jungleGreen,
    onSecondary = Color.White,

    background = chBackground,
    onBackground = Color.Black,
    surface = Color.White,

    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSurface = Color.Black,
    */
)

@Composable
fun ClubhouseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        LightColorPalette // No dark theme for now
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}