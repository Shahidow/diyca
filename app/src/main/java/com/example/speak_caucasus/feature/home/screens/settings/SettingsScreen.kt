package com.example.speak_caucasus.feature.home.screens.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.navigation.ScreenRoutes
import com.example.speak_caucasus.ui.coponents.CustomBoxContainer
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.coponents.CustomDialog
import com.example.speak_caucasus.ui.coponents.CustomTextButtonColored
import com.example.speak_caucasus.ui.coponents.CustomTextField
import com.example.speak_caucasus.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val viewModel: SettingsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SettingsEffect.NavigateBack -> navHostController.popBackStack()
                is SettingsEffect.NavigateToLogin -> {
                    navHostController.navigate(ScreenRoutes.LoginRout) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

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

    // 1. Диалог выхода
    if (state.showLogoutDialog) {
        CustomDialog(
            title = stringResource(R.string.log_out),
            message = stringResource(R.string.logout_confirmation_message),
            confirmButtonText = stringResource(R.string.action_yes),
            onConfirm = { viewModel.dispatch(SettingsMsg.LogOut) },
            dismissButtonText = stringResource(R.string.action_cancel),
            onDismiss = { viewModel.dispatch(SettingsMsg.DismissDialogs) }
        )
    }

    // 2. Диалог-предупреждение об удалении
    if (state.showDeleteWarningDialog) {
        CustomDialog(
            title = stringResource(R.string.warning_title),
            message = stringResource(R.string.delete_account_confirmation_message),
            confirmButtonText = stringResource(R.string.action_yes),
            onConfirm = { viewModel.dispatch(SettingsMsg.RemoveProfileConfirmed) },
            dismissButtonText = stringResource(R.string.action_cancel),
            onDismiss = { viewModel.dispatch(SettingsMsg.DismissDialogs) }
        )
    }

    // 3. Диалог смены имени
    if (state.showChangeNameDialog) {
        CustomDialog(
            title = stringResource(R.string.username),
            message = stringResource(R.string.change_username_prompt),
            confirmButtonText = stringResource(R.string.action_save),
            onConfirm = { viewModel.dispatch(SettingsMsg.UserNameChangeConfirmed) },
            dismissButtonText = stringResource(R.string.action_cancel),
            onDismiss = { viewModel.dispatch(SettingsMsg.DismissDialogs) },
            showTextField = true,
            textFieldValue = state.changeNameInput,
            onValueChange = { viewModel.dispatch(SettingsMsg.UserNameChanged(it)) },
            textFieldLabel = stringResource(R.string.new_username_label),
            isPassword = false,
            error = state.error?.let { stringResource(it) },
        )
    }

    // 4. Диалог смены пароля
    if (state.showChangePassDialog) {
        CustomDialog(
            title = stringResource(R.string.change_password),
            message = stringResource(R.string.think_password),
            confirmButtonText = stringResource(R.string.action_save),
            onConfirm = { viewModel.dispatch(SettingsMsg.PassChangeConfirmed) },
            dismissButtonText = stringResource(R.string.action_cancel),
            onDismiss = { viewModel.dispatch(SettingsMsg.DismissDialogs) },
            showTextField = true,
            textFieldValue = state.changePasswordInput,
            onValueChange = { viewModel.dispatch(SettingsMsg.ChangePassChanged(it)) },
            textFieldLabel = stringResource(R.string.new_password_label),
            error = state.error?.let { stringResource(it) },
        )
    }

    // 5. Диалог подтверждения паролем
    if (state.showPasswordDialog) {
        CustomDialog(
            title = stringResource(R.string.confirmation_title),
            message = stringResource(R.string.enter_password_prompt),
            confirmButtonText = stringResource(R.string.action_confirm),
            onConfirm = { viewModel.dispatch(SettingsMsg.FinalActionConfirmed) },
            dismissButtonText = stringResource(R.string.action_cancel),
            onDismiss = { viewModel.dispatch(SettingsMsg.DismissDialogs) },
            showTextField = true,
            textFieldValue = state.confirmPasswordInput,
            onValueChange = { viewModel.dispatch(SettingsMsg.ConfirmPasswordChanged(it)) },
            textFieldLabel = stringResource(R.string.password),
            error = state.error?.let { stringResource(it) },
            isLoading = state.isLoading
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        SettingsTitle(viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_8))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            SettingsUserData(state.pic, state.userName, viewModel)
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
            CustomTextField(
                value = state.userEmail,
                onValueChange = {},
                label = stringResource(R.string.mail),
                borderColor = MaterialTheme.colorScheme.outline,
                backgroundColor = MaterialTheme.colorScheme.surface,
                isBorder = true,
                isEnabled = false
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            CustomButtonColored(
                onClick = { viewModel.dispatch(SettingsMsg.PassChangeClicked) },
                text = stringResource(R.string.change_password),
                isOutlined = true,
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_24))
            SettingsLanguage()
            Spacer(modifier = Modifier.height(Dimens.Padding_24))
            CustomButtonColored(
                onClick = { viewModel.dispatch(SettingsMsg.LogOutClicked) },
                text = stringResource(R.string.log_out),
                isOutlined = true,
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
            CustomTextButtonColored(
                text = stringResource(R.string.delete_account),
                onClick = { viewModel.dispatch(SettingsMsg.RemoveProfileClicked) },
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
        }
    }
}

@Composable
fun SettingsTitle(viewModel: SettingsViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon, text) = createRefs()
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                }
                .clickable { viewModel.dispatch(SettingsMsg.NavigateBack) }
        )
        Text(stringResource(R.string.account_settings), modifier = Modifier.constrainAs(text) {
            top.linkTo(icon.top)
            bottom.linkTo(icon.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun SettingsUserData(pic: Int, userName: String, viewModel: SettingsViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon1, text, icon2) = createRefs()
        Icon(
            painter = painterResource(pic),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .constrainAs(icon1) {
                    top.linkTo(parent.top, margin = Dimens.Padding_36)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            userName,
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
                .clickable { viewModel.dispatch(SettingsMsg.UserNameChangeClicked) }
        )
    }
}

@Composable
fun SettingsLanguage() {
    CustomBoxContainer(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
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
                shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.Padding_12)
            )
            .clickable(enabled = false) { onClick() } // Пока выключено
            .padding(horizontal = Dimens.Padding_16),
        contentAlignment = Alignment.CenterStart
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text(
                text = currentValue,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}