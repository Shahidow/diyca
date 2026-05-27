package com.example.diyca.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.diyca.R

val Typography = Typography(
    // Крупные заголовки
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        fontSize = Dimens.TextSize_32
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        fontSize = Dimens.TextSize_24
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_22
    ),

    // Основной текст
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_16
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_regular)),
        fontSize = Dimens.TextSize_16
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_regular)),
        fontSize = Dimens.TextSize_14
    ),

    // Крупные заголовки экранов
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        fontSize = Dimens.TextSize_20
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_20
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_18
    ),

    // Подзаголовки
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        fontSize = Dimens.TextSize_16
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_16
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_regular)),
        fontSize = Dimens.TextSize_16
    ),

    //Подписи и мелкие элементы, кнопки
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_16
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_medium)),
        fontSize = Dimens.TextSize_14
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito_regular)),
        fontSize = Dimens.TextSize_10
    )

        /*
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