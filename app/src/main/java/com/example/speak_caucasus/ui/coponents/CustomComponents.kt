package com.example.speak_caucasus.ui.coponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.speak_caucasus.ui.theme.BlackText
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Grey92
import com.example.speak_caucasus.ui.theme.MediumGray
import com.example.speak_caucasus.ui.theme.PrimaryTeal

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    isCode: Boolean = false,
    isBorder: Boolean = false,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Grey92,
    isEnabled: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val contentType = when {
        isEmail -> ContentType.Username
        isPassword -> ContentType.Password
        else -> null
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.Size_56)
            .background(color = backgroundColor, shape = RoundedCornerShape(Dimens.Padding_12))
            .then(
                if (contentType != null) {
                    Modifier.semantics {
                        this.contentType = contentType
                    }
                } else Modifier
            )
            .border(
                width = 1.dp,
                color = when {
                    isBorder -> borderColor
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(Dimens.Padding_12)
            ),
        value = if (isCode) value.uppercase() else value,
        onValueChange = { newText ->
            if (isCode) onValueChange(newText.uppercase()) else onValueChange(
                newText
            )
        },
        enabled = isEnabled,
        label = { Text(label, color = MediumGray, fontSize = Dimens.TextSize_14) },
        textStyle = LocalTextStyle.current.copy(textAlign = if (isCode) TextAlign.Center else TextAlign.Start),

        // видимость пароля
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                        tint = MediumGray
                    )
                }
            }
        } else null,

        colors = TextFieldDefaults.colors(
            focusedTextColor = BlackText,
            unfocusedTextColor = BlackText,
            disabledTextColor = BlackText,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(Dimens.Padding_12),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = when {
                isPassword -> KeyboardType.Password
                isEmail -> KeyboardType.Email
                isCode -> KeyboardType.Number
                else -> KeyboardType.Text
            }
        )
    )
}

@Composable
fun CustomProgressBar(
    progress: Float,
    progressColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .padding(horizontal = 4.dp)
            .background(color = backgroundColor, RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(clampedProgress)
                .background(
                    color = progressColor,
                    shape = RoundedCornerShape(4.dp)
                )
        )
    }
}

@Composable
fun CustomDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    dismissButtonText: String? = null,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    showTextField: Boolean = false,
    textFieldValue: String = "",
    onValueChange: (String) -> Unit = {},
    textFieldLabel: String = "",
    isPassword: Boolean = true,
    error: String? = null,
    isLoading: Boolean = false
) {
    Dialog(
        onDismissRequest = { if (!isLoading) onDismiss?.invoke() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = !isLoading,
            dismissOnClickOutside = !isLoading
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(Dimens.Padding_24)
                )
                .padding(Dimens.Padding_24)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.Padding_16)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                if (!error.isNullOrEmpty() && !isLoading) {
                    Text(
                        text = error,
                        fontSize = Dimens.TextSize_10,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = Dimens.Padding_16)
                    )
                }

                if (showTextField) {
                    CustomTextField(
                        value = textFieldValue,
                        onValueChange = onValueChange,
                        label = textFieldLabel,
                        isBorder = true,
                        isPassword = isPassword,
                        isEnabled = !isLoading
                    )
                }

                if (isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(
                        modifier = Modifier.padding(Dimens.Padding_16),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
                    ) {
                        val buttonModifier = Modifier.weight(1f)

                        CustomButtonColored(
                            text = confirmButtonText,
                            onClick = onConfirm,
                            height = Dimens.Size_48,
                            modifier = buttonModifier
                        )

                        if (onDismiss != null && dismissButtonText != null) {
                            CustomButtonColored(
                                text = dismissButtonText,
                                onClick = onDismiss,
                                height = Dimens.Size_48,
                                isOutlined = true,
                                modifier = buttonModifier
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomCircularProgress(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    lessonProgress: Float,
    taskProgress: Float,
    size: Dp = 100.dp,
) {
    val strokeWidth = size * 0.085f
    val innerCircleSize = size * 0.68f
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        CircularProgressIndicator(
            progress = { lessonProgress },
            modifier = Modifier.size(size),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            strokeWidth = strokeWidth,
        )
        CircularProgressIndicator(
            progress = { taskProgress },
            modifier = Modifier.size(innerCircleSize),
            color = PrimaryTeal,
            trackColor = PrimaryTeal.copy(alpha = 0.2f),
            strokeWidth = strokeWidth,
        )
    }
}

@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = Dimens.TextSize_16,
    minFontSize: TextUnit = Dimens.TextSize_10
) {
    val textMeasurer = rememberTextMeasurer()
    var fontSize by remember { mutableStateOf(maxFontSize) }

    LaunchedEffect(text) {
        while (fontSize > minFontSize) {
            val result = textMeasurer.measure(
                text = text,
                style = TextStyle(fontSize = fontSize),
                maxLines = 1
            )
            if (!result.hasVisualOverflow) break
            fontSize *= 0.9f
        }
    }

    Text(
        text = text,
        maxLines = 1,
        fontSize = fontSize,
        modifier = modifier
    )
}