package com.example.speak_caucasus.ui.coponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.speak_caucasus.ui.theme.DarkGreen
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Grey92
import com.example.speak_caucasus.ui.theme.Grey96
import com.example.speak_caucasus.ui.theme.LightGreen


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
){
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.Padding_56),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = Dimens.TextSize_14) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(Dimens.Padding_12),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
    )
}

@Composable
fun CustomProgressBar(
    progress: Float, // Прогресс от 0.0 до 1.0
    gradientColors: List<Color> = listOf(DarkGreen, LightGreen), // Градиент
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .padding(horizontal = 4.dp)
            .background(Grey96, RoundedCornerShape(4.dp))

    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        ) {
            // Отрисовка градиента
            val width = size.width * progress
            drawRoundRect(
                brush = Brush.horizontalGradient(gradientColors),
                size = Size(width, size.height),
                cornerRadius = CornerRadius(4.dp.toPx()),
            )
        }
    }
}

@Composable
fun CustomBoxContainer(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.clickable { onClick() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Grey92,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
    ) {
        content()
    }
}
