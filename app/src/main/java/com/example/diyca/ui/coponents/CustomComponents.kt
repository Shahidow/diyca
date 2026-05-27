package com.example.diyca.ui.coponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.diyca.R
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Grey92
import com.example.diyca.ui.theme.MediumGray
import com.example.diyca.util.ErrorType

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
    borderColor: Color = MaterialTheme.colorScheme.outline,
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    showDeleteButton: Boolean = false,
    onDeleteClick: () -> Unit = {}
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
        readOnly = readOnly,
        label = if (label.isNotEmpty()) {
            {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        } else null,
        textStyle = LocalTextStyle.current.copy(textAlign = if (isCode) TextAlign.Center else TextAlign.Start),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = when {
            isPassword -> {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = null,
                            tint = MediumGray
                        )
                    }
                }
            }

            showDeleteButton && value.isNotEmpty() -> {
                {
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Backspace,
                            contentDescription = null,
                            tint = MediumGray
                        )
                    }
                }
            }

            else -> null
        },

        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
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
fun CustomDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    dismissButtonText: String? = null,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    onCloseRequest: (() -> Unit)? = null,
    showTextField: Boolean = false,
    textFieldValue: String = "",
    onValueChange: (String) -> Unit = {},
    textFieldLabel: String = "",
    isPassword: Boolean = true,
    error: String? = null,
    isLoading: Boolean = false
) {
    Dialog(
        onDismissRequest = { if (!isLoading) (onCloseRequest ?: onDismiss)?.invoke() },
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
                    color = MaterialTheme.colorScheme.background,
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
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                if (!error.isNullOrEmpty() && !isLoading) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = Dimens.Padding_16),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                if (showTextField) {
                    CustomTextField(
                        value = textFieldValue,
                        onValueChange = onValueChange,
                        label = textFieldLabel,
                        isPassword = isPassword,
                        isEnabled = !isLoading
                    )
                }

                if (isLoading) {
                    CircularProgressIndicator(
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
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    maxFontSize: TextUnit = style.fontSize,
    minFontSize: TextUnit = Dimens.TextSize_10
) {
    val textMeasurer = rememberTextMeasurer()
    var fontSize by remember { mutableStateOf(maxFontSize) }
    LaunchedEffect(text, maxFontSize, minFontSize) {
        fontSize = maxFontSize
        var currentSize = maxFontSize
        while (currentSize > minFontSize) {
            val result = textMeasurer.measure(
                text = text,
                style = style.copy(fontSize = currentSize),
                maxLines = 1
            )
            if (result.hasVisualOverflow) {
                currentSize = (currentSize.value * 0.9f).sp
                if (currentSize <= minFontSize) {
                    fontSize = minFontSize
                    break
                }
            } else {
                fontSize = currentSize
                break
            }
        }
        if (currentSize <= minFontSize) {
            fontSize = minFontSize
        }
    }
    Text(
        text = text,
        style = style,
        maxLines = 1,
        fontSize = fontSize,
        modifier = modifier
    )
}

@Composable
fun CustomErrorBox(
    onClick: () -> Unit,
    errorType: ErrorType,
    modifier: Modifier = Modifier,
    imageSize: Dp = Dimens.Size_150,
    isButtonEnabled: Boolean = true
) {
    val errorMessage = when (errorType) {
        is ErrorType.NetworkError -> R.string.no_internet
        is ErrorType.ServerError -> R.string.server_error
        is ErrorType.Forbidden -> R.string.forbidden
        else -> R.string.unknown_error
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = if (errorType is ErrorType.NetworkError) painterResource(R.drawable.ic_error_internet)
                else painterResource(R.drawable.ic_error_server),
                modifier = Modifier.size(imageSize * 0.7f),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        Text(
            text = stringResource(errorMessage),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.error
        )
        if (isButtonEnabled) {
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            CustomButtonColored(
                onClick = { onClick() },
                text = stringResource(R.string.action_update),
                modifier = Modifier.width(Dimens.Size_150),
                height = Dimens.Size_32
            )
        }
    }
}