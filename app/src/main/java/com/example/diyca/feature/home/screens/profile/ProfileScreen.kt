package com.example.diyca.feature.home.screens.profile

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomRewardBox
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.coponents.shimmerBrush
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Grey92
import org.koin.androidx.compose.koinViewModel

@SuppressLint("LocalContextGetResourceValueCall")
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
                            context.getString(R.string.message_download_the_app)
                        )
                    }
                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            context.getString(R.string.share_via)
                        )
                    )
                }

                is ProfileEffect.NavigateBack -> navHostController.popBackStackSafe()
                is ProfileEffect.NavigateToSettings -> navHostController.navigateSafe(ScreenRoutes.SettingsRout)
                is ProfileEffect.RateUs -> {                                                                                    //TODO
                    val uri = "market://details?id=your.app.id".toUri()
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri).apply {
                        addFlags(
                            Intent.FLAG_ACTIVITY_NO_HISTORY or
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        )
                    }
                    try {
                        context.startActivity(goToMarket)
                    } catch (_: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://play.google.com/store/apps/details?id=your.app.id".toUri()
                            )
                        )
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        ProfileHeader(viewModel, state.avatar, state.userName)
        Spacer(modifier = Modifier.height(Dimens.Padding_32))

        ProfileSettingsButton(viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_32))

        Text(
            stringResource(R.string.users_achievements),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = Dimens.Padding_16)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        UserRewardsSection(state.rewards, Modifier.weight(1f))
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
        Spacer(modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun ProfileHeader(viewModel: ProfileViewModel, avatar: UserAvatar?, userName: String) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon1, icon2, text) = createRefs()
        CustomBackButton(
            onClick = { viewModel.dispatch(ProfileMsg.BackClicked) },
            modifier = Modifier.constrainAs(icon1) {
                top.linkTo(parent.top, margin = Dimens.Padding_36)
                start.linkTo(parent.start)
            }
        )
        Box(
            modifier = Modifier
                .size(Dimens.Size_100)
                .constrainAs(icon2) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clip(CircleShape)
                .then(
                    if (avatar == null) {
                        Modifier.background(shimmerBrush(showShimmer = true))
                    } else {
                        Modifier.border(
                            width = Dimens.Size_1,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            avatar?.let { avatar ->
                Icon(
                    painter = painterResource(avatar.resId),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(text) {
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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_settings_button),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = Dimens.Padding_24)
            )
            Text(
                text = stringResource(R.string.account_settings),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
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
fun UserRewardsSection(rewards: List<Reward>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Grey92,
                RoundedCornerShape(Dimens.RoundedCorner_12)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (rewards.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_award),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_4))
                Text(
                    text = stringResource(R.string.no_rewards),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.Padding_16),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Padding_8),
                verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8),
                contentPadding = PaddingValues(horizontal = Dimens.Padding_8)
            ) {
                items(rewards.size) { index ->
                    CustomRewardBox(rewards[index])
                }
            }
        }
    }
}