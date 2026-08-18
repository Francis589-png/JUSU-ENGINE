package com.jusu.engine.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val JusuDark = darkColorScheme(
    primary = Color(0xFF5EE7FF),
    secondary = Color(0xFF7B61FF),
    tertiary = Color(0xFFFF4FD8),
    background = Color(0xFF05070B),
    surface = Color(0xFF0C111A),
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun JusuEngineTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = JusuDark, content = content)
}
