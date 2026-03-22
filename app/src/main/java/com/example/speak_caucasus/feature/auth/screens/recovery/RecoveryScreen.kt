package com.example.speak_caucasus.feature.auth.screens.recovery

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.navigation.ScreenRoutes
import com.example.speak_caucasus.ui.coponents.CustomBackButton
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextField
import com.example.speak_caucasus.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecoveryScreen(navHostController: NavHostController) {
    val viewModel: RecoveryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is RecoveryEffect.ShowToast -> Toast.makeText(
                    context,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()

                is RecoveryEffect.ClickBack -> navHostController.navigate(ScreenRoutes.LoginRout) {
                    launchSingleTop = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.Padding_16)
            ) {
                CustomBackButton(onClick = { viewModel.dispatch(RecoveryMsg.ClickBack) })
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimens.Padding_16)
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_logo_hadiyca),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.75f)
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_8))

                Text(
                    stringResource(state.screenType.previewText),
                    fontSize = Dimens.TextSize_24,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))

                Text(
                    stringResource(state.screenType.meinText),
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))

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
                    value = when(state.screenType){
                        RecoveryScreenType.VerifyResetCode -> state.checkMailCode
                        RecoveryScreenType.ResetPassword -> state.newPassword
                        RecoveryScreenType.PasswordReset -> state.email
                    },
                    onValueChange = { viewModel.dispatch(RecoveryMsg.TextChanged(it, state.screenType)) },
                    label = stringResource(state.screenType.textFieldLabel),
                    isPassword = state.screenType.isPassword,
                    isCode = state.screenType.isCode
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))

                CustomButtonColored(
                    onClick = { viewModel.dispatch(RecoveryMsg.ButtonClicked) },
                    text = stringResource(state.screenType.buttonText)
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_24))
                if (state.screenType.isCode) CustomTextButtonColored(
                    text = if (!state.isResendEnabled) {
                        stringResource(R.string.resend_code_timer, state.resendTimer)
                    } else {
                        stringResource(R.string.resend_code)
                    },
                    onClick = {
                        if (state.isResendEnabled) viewModel.dispatch(RecoveryMsg.ResendCodeClicked)
                    },
                    color = if (state.isResendEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(Dimens.Padding_32))
            }
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