package com.example.speak_caucasus.ui.coponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Green
import com.example.speak_caucasus.ui.theme.Grey96
import com.example.speak_caucasus.ui.theme.SoftMint

@Composable
fun CustomTextButtonColored(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = Modifier.clickable { onClick() },
        color = MaterialTheme.colorScheme.primary,
        fontSize = Dimens.TextSize_16
    )
}

@Composable
fun CustomButtonColored(
    onClick: () -> Unit,
    text: String,
    isOutlined: Boolean = false,
    height: Dp = Dimens.Padding_56
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        onClick = { onClick() },
        border = if (isOutlined) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
        colors = if (isOutlined) {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        },
        shape = RoundedCornerShape(Dimens.Padding_12),
    ) {
        Text(text, fontSize = Dimens.TextSize_16)
    }
}

@Composable
fun CustomTaskButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        enabled = !isSelected,
        shape = RoundedCornerShape(Dimens.RoundedCorner_10),
        color = Grey96,
        contentColor = Color.Black,
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = Dimens.Padding_4)
            .alpha(if (isSelected) 0.0f else 1f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(Dimens.Padding_8)
        ) {
            Text(
                text = text,
                fontSize = Dimens.TextSize_16
            )
        }
    }
}

@Composable
fun CustomSectionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(Dimens.RoundedCorner_16),
        color = if (isSelected) {
            SoftMint
        } else {
            Grey96
        },
        contentColor = if (isSelected) {
            Green
        } else {
            Color.Black
        },
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = Dimens.Padding_4)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = Dimens.Padding_8, vertical = Dimens.Padding_2)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}