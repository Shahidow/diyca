package com.example.speak_caucasus.feature.start.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.speak_caucasus.R
import com.example.speak_caucasus.data.network.sendPingRequest
import com.example.speak_caucasus.ui.bottom_nav.ScreenRoutes
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextField
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme


@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(navHostController: NavHostController) {
    Speak_CaucasusTheme {
        var phone by remember { mutableStateOf("") }
        var isPhoneFocused by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf("") }
        var isPasswordFocused by remember { mutableStateOf(false) }
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.Asset("anim.json")
        )
        val progress by derivedStateOf {
            if(isPhoneFocused) {
                if (phone.isEmpty()) 0.1f else 0.1f + phone.length.coerceAtMost(40) / 100f
            } else  {
                if (isPasswordFocused) 1f else 0f
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = Dimens.Padding_16),
        ) {
            LottieAnimation(
                modifier = Modifier.size(200.dp),
                composition = composition,
                progress = progress
            )
            CustomTextField(
                phone,
                onValueChange = { phone = it },
                stringResource(R.string.phone_num),
                modifier = Modifier.onFocusChanged { focusState ->
                    isPhoneFocused = focusState.isFocused
                }
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomTextField(
                password,
                onValueChange = { password = it },
                stringResource(R.string.password_label),
                isPassword = true,
                modifier = Modifier.onFocusChanged { focusState ->
                    isPasswordFocused = focusState.isFocused
                }
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_24))

            CustomButtonColored(onClick = {
                navHostController.navigate(ScreenRoutes.HOME_SCREEN)
            }, stringResource(R.string.button_login))
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomButtonColored(
                onClick = {
                    sendPingRequest()
                    //navHostController.navigate(ScreenRoutes.REGISTRATION_SCREEN)
                },
                stringResource(R.string.button_register),
                isOutlined = true
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))

            CustomTextButtonColored(stringResource(R.string.forgot_password), onClick = {})
            Spacer(modifier = Modifier.height(Dimens.Padding_48))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painterResource(R.drawable.ic_google),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(Dimens.Padding_48))
                Icon(
                    painterResource(R.drawable.ic_vk),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(112.dp))
        }
    }
}