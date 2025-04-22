package com.example.speak_caucasus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.speak_caucasus.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_16
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_16
    )

    /* Other default text styles to override
    title
    taskTitle
    textFieldLabel

    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

    val MyCustomTextStyle = TextStyle(
    fontFamily = FontFamily.Default, // Шрифт
    fontWeight = FontWeight.Bold,    // Жирность текста
    fontSize = 18.sp,                // Размер текста
    color = Color.Black,             // Цвет текста
    letterSpacing = 0.5.sp,          // Межбуквенный интервал
    lineHeight = 24.sp               // Межстрочный интервал
)
    */
)