package com.example.freelancerportfolio.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = AccentDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    error = Error,
    onPrimary = TextOnPrimary,
    onSecondary = TextOnSecondary,
    onBackground = TextPrimary,
    onSurface = TextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    tertiary = AccentLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = Error,
    onPrimary = TextOnPrimary,
    onSecondary = TextOnSecondary,
    onBackground = TextPrimary,
    onSurface = TextSecondary
)

@Composable
fun FreelancerPortfolioTheme(
    isDarkMode: Boolean,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDarkMode -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = colorScheme.primary)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
