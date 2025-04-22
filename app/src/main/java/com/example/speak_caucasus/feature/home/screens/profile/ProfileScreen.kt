package com.example.speak_caucasus.feature.home.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.coponents.CustomBoxContainer
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextButtonColored
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Green
import com.example.speak_caucasus.ui.theme.Grey92
import com.example.speak_caucasus.ui.theme.SoftMint
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    Speak_CaucasusTheme {
        var isChecked by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.Padding_16)
                .verticalScroll(scrollState)
        ) {
            ProfileHeader(navHostController)
            Spacer(modifier = Modifier.height(Dimens.Padding_32))

            AccountSettingsButton()
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            NotificationsSwitch(isChecked) { isChecked = it }
            Spacer(modifier = Modifier.height(Dimens.Padding_32))

            Text(
                stringResource(R.string.achievements),
                modifier = Modifier.padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            AchievementSection()
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            CustomButtonColored(onClick = {}, stringResource(R.string.invite_friends), true)
            Spacer(modifier = Modifier.height(Dimens.Padding_16))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomTextButtonColored(stringResource(R.string.rate_app), onClick = { })
                CustomTextButtonColored(stringResource(R.string.contacts), onClick = { })
            }
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
        }
    }
}

@Composable
fun ProfileHeader(navHostController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon1, icon2, text) = createRefs()
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .constrainAs(icon1) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                }
                .clickable { navHostController.popBackStack() }
        )
        Icon(
            painter = painterResource(R.drawable.ic_avatar_ph),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .constrainAs(icon2) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            stringResource(R.string.username),
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(icon2.bottom, margin = Dimens.Padding_32)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
fun AccountSettingsButton() {
    CustomBoxContainer {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_settings_button),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.padding(start = Dimens.Padding_24)
            )
            Text(
                stringResource(R.string.account_settings),
                modifier = Modifier.padding(
                    start = Dimens.Padding_8,
                    top = Dimens.Padding_16,
                    bottom = Dimens.Padding_16
                )
            )
        }

    }
}

@Composable
fun NotificationsSwitch(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    CustomBoxContainer(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onCheckedChange(!isChecked) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.notifications),
                modifier = Modifier.padding(
                    start = Dimens.Padding_24,
                    top = Dimens.Padding_16,
                    bottom = Dimens.Padding_16
                )
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(end = Dimens.Padding_24),

                enabled = true,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Green,
                    uncheckedThumbColor = Color.Gray,
                    checkedTrackColor = SoftMint,
                    uncheckedTrackColor = Color.LightGray,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent
                ),
            )
        }

    }
}

@Composable
fun AchievementSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(198.dp)
            .border(
                width = 1.dp,
                color = Grey92,
                RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { }

    ) {}
}