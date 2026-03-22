package com.example.diyca.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Blue500,
    onPrimary = White,
    secondary = BlueBackgroundLight,
    onSecondary = BlackText,
    tertiary = Blue600,
    background = White,
    onBackground = BlackText,
    surface = BlueInputSurface,
    onSurface = BlackText,
    error = Color.Red,
    onError = Color.White,
    outline = BlueOutline
)

private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = White,

    secondary = BlueBackgroundLight,
    onSecondary = BlackText,

    tertiary = Blue600,

    background = White,
    onBackground = BlackText,

    surface = BlueInputSurface,
    onSurface = BlackText,

    error = Color.Red,
    onError = Color.White,

    outline = BlueOutline

    /* default colors to override
    *primary = Blue500,               // Основной цвет интерфейса (кнопки, акценты)
    *onPrimary = Color.White,         // Цвет поверх primary (иконки, текст на кнопке)

    *secondary = BlueBackgroundLight, // Вторичный цвет (менее заметные акценты)
    *onSecondary = Color.White,       // Цвет поверх secondary

    *tertiary = White,                // Третичный цвет (доп. элементы, акценты)
    onTertiary = Color.Black,        // Цвет поверх tertiary

    *background = Color(0xFFFFFBFE),  // Основной фон приложения
    *onBackground = Color(0xFF1C1B1F),// Цвет контента на фоне

    *surface = White,                 // Фон карточек, панелей, bottom sheets
    *onSurface = Color(0xFF1C1B1F),   // Цвет текста/иконок на surface

    *surfaceVariant = Color(0xFFE7E0EC),     // Альтернативный surface для вариаций
    onSurfaceVariant = Color(0xFF49454F),   // Контент на surfaceVariant

    *error = Color(0xFFB3261E),       // Цвет ошибок
    *onError = Color.White,           // Текст на фоне ошибки

    *outline = Color(0xFF79747E),     // Цвет рамок, divider-ов, неактивных полей

    inverseSurface = Color(0xFF313033),     // Обратный цвет surface (например, в snackbar)
    inverseOnSurface = Color(0xFFF4EFF4),   // Контент поверх inverseSurface
    inversePrimary = Color(0xFF6750A4),     // Обратный к primary (например, для переключения тем)

    surfaceTint = Blue500            // Цвет блика/подсветки для surface (влияет на elevation)
    */
)

@Composable
fun diycaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme //if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}