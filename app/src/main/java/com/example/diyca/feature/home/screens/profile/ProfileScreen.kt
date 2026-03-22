package com.example.diyca.feature.home.screens.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Green
import com.example.diyca.ui.theme.Grey92
import com.example.diyca.ui.theme.SoftMint
import androidx.constraintlayout.compose.ConstraintLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val viewModel: ProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ProfileEffect.InviteFriends -> {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Привет! Скачай Speak Caucasus: https://play.google.com/store/apps/details?id=your.app.id"
                        )
                    }
                    context.startActivity(Intent.createChooser(intent, "Поделиться через"))
                }

                is ProfileEffect.NavigateBack -> navHostController.popBackStack()
                is ProfileEffect.NavigateTo -> navHostController.navigate(effect.route)
                is ProfileEffect.RateUs -> {
                    val uri = Uri.parse("market://details?id=your.app.id")
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri).apply {
                        addFlags(
                            Intent.FLAG_ACTIVITY_NO_HISTORY or
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        )
                    }
                    try {
                        context.startActivity(goToMarket)
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=your.app.id")
                            )
                        )
                    }
                }
            }
        }
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.Padding_16)
            .verticalScroll(scrollState)
    ) {
        ProfileHeader(viewModel, state.pic, state.userName)
        Spacer(modifier = Modifier.height(Dimens.Padding_32))

        ProfileSettingsButton(viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        ProfileNotificationsSwitch(state.notifications) {
            viewModel.dispatch(
                ProfileMsg.NotificationChange(
                    it
                )
            )
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_32))

        Text(
            stringResource(R.string.achievements),
            modifier = Modifier.padding(start = Dimens.Padding_16)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        ProfileAchievementSection()
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        CustomButtonColored(
            onClick = { viewModel.dispatch(ProfileMsg.InviteFriendsClicked) },
            stringResource(R.string.invite_friends),
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomTextButtonColored(
                stringResource(R.string.rate_app),
                onClick = { viewModel.dispatch(ProfileMsg.RateUsClicked) })
            CustomTextButtonColored(stringResource(R.string.contacts), onClick = { })
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
    }
}

@Composable
fun ProfileHeader(viewModel: ProfileViewModel, pic: Int, userName: String) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon1, icon2, text) = createRefs()
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .constrainAs(icon1) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                }
                .clickable { viewModel.dispatch(ProfileMsg.BackClicked) }
        )
        Icon(
            painter = painterResource(pic),
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
            userName,
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
fun ProfileSettingsButton(viewModel: ProfileViewModel) {
    CustomBoxContainer(
        onClick = { viewModel.dispatch(ProfileMsg.GoToSettings) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_settings_button),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
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
fun ProfileNotificationsSwitch(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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
fun ProfileAchievementSection() {
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