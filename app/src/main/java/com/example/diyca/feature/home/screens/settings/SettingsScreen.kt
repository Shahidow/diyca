package com.example.diyca.feature.home.screens.settings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.feature.home.screens.settings.models.SettingsDialog
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomDialog
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.coponents.CustomTextField
import com.example.diyca.ui.coponents.shimmerBrush
import com.example.diyca.ui.navigation.navigateAndClearStack
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val viewModel: SettingsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SettingsEffect.NavigateBack -> navHostController.popBackStackSafe()
                is SettingsEffect.NavigateToLogin -> navHostController.navigateAndClearStack(
                    ScreenRoutes.LoginRout
                )

                is SettingsEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        context.getString(effect.messageRes),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    SettingsDialogs(state, viewModel)

    if (state.isAvatarPickerVisible) {
        AvatarSelectionDialog(
            onDismiss = { viewModel.dispatch(SettingsMsg.AvatarPickerDismissed) },
            onAvatarClick = { avatar ->
                viewModel.dispatch(SettingsMsg.AvatarSelected(avatar.key))
                viewModel.dispatch(SettingsMsg.AvatarPickerDismissed)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        SettingsHeader(viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_8))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            SettingsUserData(state.avatar, state.userName, viewModel)
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
            CustomTextField(
                value = state.userEmail,
                onValueChange = {},
                label = stringResource(R.string.mail),
                borderColor = MaterialTheme.colorScheme.outline,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                isBorder = true,
                isEnabled = false
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            CustomButtonColored(
                onClick = { viewModel.dispatch(SettingsMsg.ShowDialog(SettingsDialog.ChangePassDialog)) },
                text = stringResource(R.string.change_password),
                isOutlined = true,
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_24))
            SettingsLanguage()
            Spacer(modifier = Modifier.height(Dimens.Padding_24))
            CustomButtonColored(
                onClick = { viewModel.dispatch(SettingsMsg.ShowDialog(SettingsDialog.LogOutDialog)) },
                text = stringResource(R.string.log_out),
                isOutlined = true,
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
            CustomTextButtonColored(
                text = stringResource(R.string.clear_progress),
                onClick = { viewModel.dispatch(SettingsMsg.ShowDialog(SettingsDialog.ClearProgressDialog)) },
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
            CustomTextButtonColored(
                text = stringResource(R.string.delete_account),
                onClick = { viewModel.dispatch(SettingsMsg.ShowDialog(SettingsDialog.DeleteWarningDialog)) },
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
        }
    }
}

@Composable
fun SettingsHeader(viewModel: SettingsViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon, text) = createRefs()
        CustomBackButton(
            onClick = { viewModel.dispatch(SettingsMsg.NavigateBack) },
            modifier = Modifier.constrainAs(icon) {
                top.linkTo(parent.top, margin = Dimens.Padding_36)
                start.linkTo(parent.start)
            }
        )
        Text(text = stringResource(R.string.account_settings),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(icon.top)
                bottom.linkTo(icon.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun SettingsUserData(avatar: UserAvatar?, userName: String, viewModel: SettingsViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon1, text, icon2) = createRefs()
        Box(
            modifier = Modifier
                .size(Dimens.Size_100)
                .constrainAs(icon1) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clip(CircleShape)
                .clickable { viewModel.dispatch(SettingsMsg.AvatarPickerClicked) }
                .then(
                    if (avatar == null) {
                        Modifier.background(shimmerBrush(showShimmer = true))
                    } else {
                        Modifier.border(
                            width = Dimens.Size_2,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            avatar?.let {
                Icon(
                    painter = painterResource(it.resId),
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
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(icon1.bottom, margin = Dimens.Padding_32)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .constrainAs(icon2) {
                    top.linkTo(text.top)
                    bottom.linkTo(text.bottom)
                    start.linkTo(text.end)
                }
                .clickable { viewModel.dispatch(SettingsMsg.ShowDialog(SettingsDialog.ChangeNameDialog)) }
        )
    }
}

@Composable
fun SettingsLanguage() {
    CustomBoxContainer(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary,
        borderColor = MaterialTheme.colorScheme.outline
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding_16)
        ) {
            LanguageSelectorPlaceholder(
                currentValue = stringResource(R.string.target_language),
                onClick = { }
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            LanguageSelectorPlaceholder(
                currentValue = stringResource(R.string.app_language),
                onClick = { }
            )
        }
    }
}

@Composable
fun LanguageSelectorPlaceholder(
    currentValue: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.Size_56)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(Dimens.Padding_12)
            )
            .clickable(enabled = false) { onClick() } // Turned off for now
            .padding(horizontal = Dimens.Padding_16),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentValue,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun SettingsDialogs(state: SettingsState, viewModel: SettingsViewModel) {
    val dialog = state.activeDialog ?: return

    CustomDialog(
        title = stringResource(dialog.title),
        message = stringResource(dialog.message),
        confirmButtonText = stringResource(dialog.confirmButtonText),
        dismissButtonText = dialog.dismissButtonText?.let { dismissText ->
            stringResource(dismissText)
        },
        onConfirm = {
            val msg = when (dialog) {
                is SettingsDialog.LogOutDialog -> SettingsMsg.LogOut
                is SettingsDialog.DeleteWarningDialog -> SettingsMsg.RemoveProfileConfirmed
                is SettingsDialog.ChangeNameDialog -> SettingsMsg.UserNameChangeConfirmed
                is SettingsDialog.ChangePassDialog -> SettingsMsg.PassChangeConfirmed
                is SettingsDialog.PasswordDialog -> SettingsMsg.FinalActionConfirmed
                is SettingsDialog.ClearProgressDialog -> SettingsMsg.ClearProgressConfirmed
            }
            viewModel.dispatch(msg)
        },
        onDismiss = { viewModel.dispatch(SettingsMsg.DismissDialogs) },
        showTextField = dialog.showTextField,

        textFieldValue = when (dialog) {
            is SettingsDialog.ChangeNameDialog -> state.changeNameInput
            is SettingsDialog.ChangePassDialog -> state.changePasswordInput
            is SettingsDialog.PasswordDialog -> state.confirmPasswordInput
            else -> ""
        },

        onValueChange = { text ->
            val msg = when (dialog) {
                is SettingsDialog.ChangeNameDialog -> SettingsMsg.UserNameChanged(text)
                is SettingsDialog.ChangePassDialog -> SettingsMsg.ChangePassChanged(text)
                is SettingsDialog.PasswordDialog -> SettingsMsg.ConfirmPasswordChanged(text)
                else -> null
            }
            msg?.let { viewModel.dispatch(it) }
        },

        textFieldLabel = if (dialog.textFieldLabel != null) stringResource(dialog.textFieldLabel) else "",
        isPassword = dialog.isPassword,
        error = state.error?.let { message -> stringResource(message) },
        isLoading = state.isLoading
    )
}

@Composable
fun AvatarSelectionDialog(
    onDismiss: () -> Unit,
    onAvatarClick: (UserAvatar) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(Dimens.Padding_24)
                )
                .padding(Dimens.Padding_16)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.Padding_24)
            ) {
                Text(
                    text = stringResource(R.string.choose_avatar),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Padding_12),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Padding_12),
                ) {
                    items(UserAvatar.all.size) { index ->
                        val avatar = UserAvatar.all[index]
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(Dimens.Size_80)
                                    .clip(CircleShape)
                                    .border(
                                        width = Dimens.Size_1,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = CircleShape
                                    )
                                    .clickable { onAvatarClick(avatar) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(avatar.resId),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
                CustomButtonColored(
                    text = stringResource(R.string.action_cancel),
                    onClick = onDismiss,
                    isOutlined = true,
                    modifier = Modifier.fillMaxWidth(),
                    height = Dimens.Size_48
                )
            }
        }
    }
}