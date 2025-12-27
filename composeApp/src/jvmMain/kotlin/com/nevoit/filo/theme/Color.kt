package com.nevoit.filo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class GlasenseColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val background: Color,
    val surface: Color,
    val isDark: Boolean
)

val glasenseLightScheme = GlasenseColorScheme(
    primary = Blue500,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF89F8C7),
    onPrimaryContainer = Color(0xFF002114),
    background = Color(0xFFF3F4F6),
    surface = Color(0xFFFFFFFF),
    isDark = false
)

val glasenseDarkScheme = GlasenseColorScheme(
    primary = Blue500,
    onPrimary = Color(0xFF003826),
    primaryContainer = Color(0xFF005138),
    onPrimaryContainer = Color(0xFF89F8C7),
    background = Color(0xFF000000),
    surface = Color(0xFF1B1C1D),
    isDark = true
)

val LocalGlasenseColorScheme = staticCompositionLocalOf { glasenseLightScheme }

object GlasenseTheme {
    val colorScheme: GlasenseColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalGlasenseColorScheme.current
}

@Composable
fun GlasenseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) glasenseDarkScheme else glasenseLightScheme

    CompositionLocalProvider(
        LocalGlasenseColorScheme provides colorScheme
    ) {
        content()
    }
}