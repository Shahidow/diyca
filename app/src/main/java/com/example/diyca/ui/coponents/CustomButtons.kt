package com.example.diyca.ui.coponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.diyca.R
import com.example.diyca.ui.theme.Dimens

@Composable
fun CustomTextButtonColored(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        modifier = modifier.clickable { onClick() },
        style = MaterialTheme.typography.labelLarge,
        color = color,
    )
}

@Composable
fun CustomButtonColored(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier.fillMaxSize(),
    isEnabled: Boolean = true,
    isOutlined: Boolean = false,
    height: Dp = Dimens.Padding_56,
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(Dimens.Padding_12),
        border = if (isOutlined) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = boxModifier
                .then(
                    if (isOutlined) {
                        Modifier.background(Color.Transparent)
                    } else {
                        Modifier.background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to MaterialTheme.colorScheme.tertiary,
                                    0.5f to MaterialTheme.colorScheme.primary
                                )
                            )
                        )
                    }
                )
                .then(
                    if (!isEnabled) {
                        Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = if (isOutlined) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun CustomTaskButton(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.background
) {
    Surface(
        onClick = onClick,
        enabled = !isSelected,
        shape = RoundedCornerShape(Dimens.RoundedCorner_10),
        color = color,
        contentColor = MaterialTheme.colorScheme.onBackground,
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
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
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
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.secondary
        },
        contentColor = if (isSelected) {
            MaterialTheme.colorScheme.onBackground
        } else {
            MaterialTheme.colorScheme.primary
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
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun CustomBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(Dimens.Padding_32)
            .clickable { onClick() }
            .background(color = Color.Transparent)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(Dimens.RoundedCorner_8)
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back_1),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
        )
    }
}