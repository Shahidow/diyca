package com.example.diyca.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue500,
    onPrimary = White,
    secondary = SurfaceDark,
    onSecondary = DarkGreyText,
    tertiary = Blue600,
    background = DeepDarkBlue,
    onBackground = DarkGreyText,
    surface = SurfaceDarkElevated,
    onSurface = DarkSub,
    error = RubyRed,
    onError = White,
    outline = BlueDarkOutline,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = White,
    secondary = BlueBackgroundLight,
    onSecondary = BlackText,
    tertiary = Blue600,
    background = White,
    onBackground = BlackText,
    surface = SurfaceLightElevated,
    onSurface = NeutralGray,
    error = RubyRed,
    onError = White,
    outline = BlueOutline
)

@SuppressLint("ObsoleteSdkInt")
@Composable
fun DiycaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val windowInsetsController = WindowCompat.getInsetsController(window, view)
            windowInsetsController.isAppearanceLightStatusBars = !darkTheme
            windowInsetsController.isAppearanceLightNavigationBars = !darkTheme
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}