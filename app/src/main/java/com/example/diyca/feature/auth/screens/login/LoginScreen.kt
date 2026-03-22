package com.example.diyca.feature.auth.screens.login

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.coponents.CustomTextField
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.coponents.ImageBorder
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome -> navHostController.navigate(ScreenRoutes.HomeRout) {
                    popUpTo(ScreenRoutes.LoginRout) {
                        inclusive = true
                    }
                }

                is LoginEffect.NavigateToRegistration -> navHostController.navigate(ScreenRoutes.RegistrationRout) {
                    launchSingleTop = true
                }

                is LoginEffect.NavigateToRecovery -> navHostController.navigate(ScreenRoutes.RecoveryRout) {
                    launchSingleTop = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .blur(if (state.isLoading) 10.dp else 0.dp)
                .padding(horizontal = Dimens.Padding_16),
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Image(
                painter = painterResource(R.drawable.ic_logo_hadiyca),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))

            Text(
                text = state.error?.let { context.getString(it) } ?: "",
                fontSize = Dimens.TextSize_10,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_4))

            CustomTextField(
                state.email,
                onValueChange = { viewModel.dispatch(LoginMsg.EmailChanged(it)) },
                label = stringResource(R.string.mail),
                isEmail = true,
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomTextField(
                state.password,
                onValueChange = { viewModel.dispatch(LoginMsg.PasswordChanged(it)) },
                label = stringResource(R.string.password),
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_12))

            CustomTextButtonColored(
                stringResource(R.string.forgot_password),
                onClick = { viewModel.dispatch(LoginMsg.ForgottenPasswordClicked) },
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_24))

            CustomButtonColored(onClick = {
                viewModel.dispatch(LoginMsg.LoginClicked(state.email, state.password))
            }, stringResource(R.string.action_login), isEnabled = !state.isLoading)
            Spacer(modifier = Modifier.weight(0.5f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.no_account_q))
                Spacer(modifier = Modifier.width(Dimens.Padding_8))
                CustomTextButtonColored(
                    stringResource(R.string.action_register),
                    onClick = { viewModel.dispatch(LoginMsg.RegisterClicked) })
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
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}