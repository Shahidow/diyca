package com.example.diyca.feature.auth.screens.registration

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomDialog
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.coponents.CustomTextField
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.util.POLICY
import org.koin.androidx.compose.koinViewModel

@Composable
fun Registration(navHostController: NavHostController) {
    val viewModel: RegistrationViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is RegistrationEffect.NavigateToLogin -> navHostController.popBackStackSafe()
                is RegistrationEffect.OpenPolicyUrl -> context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        POLICY.toUri()
                    )
                )
            }
        }
    }

    if (state.isSuccess) {
        CustomDialog(
            title = stringResource(R.string.success),
            message = stringResource(R.string.registration_success_message),
            confirmButtonText = "OK",
            onConfirm = {
                viewModel.dispatch(RegistrationMsg.LoginClicked)
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .blur(if (state.isLoading) 10.dp else 0.dp)
                .padding(horizontal = Dimens.Padding_16)
                .verticalScroll(rememberScrollState())
            ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Image(
                painter = painterResource(R.drawable.ic_logo_diyca),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))

            Text(
                text = state.error?.let { stringResource(it) } ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_4))

            CustomTextField(
                state.name,
                onValueChange = { viewModel.dispatch(RegistrationMsg.NameChanged(it)) },
                label = stringResource(R.string.name)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomTextField(
                state.email,
                onValueChange = { viewModel.dispatch(RegistrationMsg.EmailChanged(it)) },
                label = stringResource(R.string.mail)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomTextField(
                state.password,
                onValueChange = { viewModel.dispatch(RegistrationMsg.PasswordChanged(it)) },
                label = stringResource(R.string.password),
                isPassword = true
            )

            PrivacyPolicyCheckbox(
                checked = state.isAgreed,
                onCheckedChange = { viewModel.dispatch(RegistrationMsg.ToggleAgreement(it)) },
                onPolicyClick = { viewModel.dispatch(RegistrationMsg.OnPolicyClick) }
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_24))

            CustomButtonColored(
                onClick = {
                    viewModel.dispatch(
                        RegistrationMsg.RegistrationClicked(
                            name = state.name,
                            email = state.email,
                            password = state.password
                        )
                    )
                },
                stringResource(R.string.action_register),
                isEnabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.have_account_q),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(Dimens.Padding_8))
                CustomTextButtonColored(stringResource(R.string.action_login), onClick = {
                    viewModel.dispatch(RegistrationMsg.LoginClicked)
                })
            }
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
fun PrivacyPolicyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onPolicyClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.Padding_8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        val annotatedString = buildAnnotatedString {
            append(stringResource(R.string.i_agree_with))
            pushStringAnnotation(tag = "policy", annotation = "policy")
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                )
            ) {
                append(stringResource(R.string.policy))
            }
            pop()
        }
        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onPolicyClick()
                    }
            }
        )
    }
}