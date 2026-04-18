package com.programming3c.prog7313part2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// dynamic colour is disabled here. BudgetQuest brand palette regardless of wallpaper
private val LightColorScheme = lightColorScheme(
    primary = BudgetGreen,
    onPrimary = OnPrimaryWhite,
    primaryContainer = BudgetGreenContainer,
    onPrimaryContainer = BudgetGreenDark,
    secondary = BudgetBlue,
    onSecondary = OnPrimaryWhite,
    secondaryContainer = BudgetBlueContainer,
    onSecondaryContainer = BudgetBlue,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = BudgetGreenLight,
    onPrimary = BudgetGreenDark,
    primaryContainer = BudgetGreenDark,
    onPrimaryContainer = BudgetGreenContainer,
    secondary = BudgetBlueLight,
    onSecondary = BudgetBlue,
    secondaryContainer = BudgetBlue,
    onSecondaryContainer = BudgetBlueLight,
    background = BackgroundDark,
    onBackground = OnPrimaryWhite,
    surface = SurfaceDark,
    onSurface = OnPrimaryWhite,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed
)

@Composable
fun Prog7313part2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
