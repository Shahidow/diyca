package com.example.speak_caucasus.feature.start.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextField
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme

@Composable
fun Registration(navHostController: NavHostController){
    Speak_CaucasusTheme {
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = Dimens.Padding_16),

            ) {
            CustomTextField(phone, onValueChange = { phone = it }, stringResource(R.string.phone_num))
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomTextField(
                password,
                onValueChange = { password = it },
                stringResource(R.string.password_label),
                isPassword = true
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_24))

            CustomButtonColored(onClick = {}, stringResource(R.string.button_register))
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
            Spacer(modifier = Modifier.height(Dimens.Padding_48))

            CustomTextButtonColored(stringResource(R.string.have_account), onClick = {
                navHostController.popBackStack()
            })
            Spacer(modifier = Modifier.height(112.dp))
        }
    }
}