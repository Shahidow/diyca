package com.example.diyca.feature.home.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.R
import com.example.diyca.domain.home.settings.SettingsInteractor
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.UserAvatar
import com.example.diyca.feature.home.screens.settings.SettingsEffect.*
import com.example.diyca.feature.home.screens.settings.models.SettingsDialog
import com.example.diyca.util.ErrorType
import com.example.diyca.util.Resource
import com.example.diyca.util.Validator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _effects = Channel<SettingsEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(SettingsMsg.LoadData)
    }

    fun dispatch(msg: SettingsMsg) {
        when (msg) {
            is SettingsMsg.LoadData -> {
                viewModelScope.launch {
                    launch {
                        settingsInteractor.getUserAvatar().collect { avatar ->
                            _state.update { it.copy(avatar = UserAvatar.fromKey(avatar)) }
                        }
                    }
                    launch {
                        val userEmail = settingsInteractor.getUserEmail()
                        settingsInteractor.getUserName().collect { newName ->
                            _state.update { it.copy(userName = newName, userEmail = userEmail) }
                        }
                    }
                }
            }

            is SettingsMsg.NavigateBack -> viewModelScope.launch { _effects.send(NavigateBack) }

            is SettingsMsg.ShowDialog -> {
                if (state.value.isLoading) return
                _state.update { it.copy(activeDialog = msg.dialog) }
            }

            is SettingsMsg.UserNameChanged -> _state.update { it.copy(changeNameInput = msg.text) }
            is SettingsMsg.ChangePassChanged -> _state.update { it.copy(changePasswordInput = msg.password) }
            is SettingsMsg.ConfirmPasswordChanged -> _state.update { it.copy(confirmPasswordInput = msg.text) }

            is SettingsMsg.LogOut -> {
                viewModelScope.launch {
                    dispatch(SettingsMsg.DismissDialogs)
                    settingsInteractor.logout()
                    _effects.send(NavigateToLogin)
                }
            }

            is SettingsMsg.UserNameChangeConfirmed -> {
                if (Validator.isValidName(state.value.changeNameInput)) {
                    _state.update {
                        it.copy(
                            activeDialog = SettingsDialog.PasswordDialog,
                            pendingAction = PendingAction.CHANGE_NAME, error = null
                        )
                    }
                } else {
                    _state.update { it.copy(error = R.string.invalid_name_format) }
                }
            }

            is SettingsMsg.PassChangeConfirmed -> {
                if (Validator.isValidPassword(state.value.changePasswordInput)) {
                    _state.update {
                        it.copy(
                            activeDialog = SettingsDialog.PasswordDialog,
                            pendingAction = PendingAction.CHANGE_PASSWORD, error = null
                        )
                    }
                } else {
                    _state.update { it.copy(error = R.string.invalid_password_format) }
                }
            }

            is SettingsMsg.ClearProgressConfirmed -> {
                if (state.value.isLoading) return
                _state.update { it.copy(isLoading = true, error = null) }
                viewModelScope.launch {
                    val result = settingsInteractor.clearProgress()
                    _state.update { it.copy(isLoading = false) }
                    when (result) {
                        is Resource.Success -> {
                            _effects.send(ShowToast(R.string.success))
                            dispatch(SettingsMsg.DismissDialogs)
                        }
                        is Resource.Error -> dispatch(SettingsMsg.Error(result.errorType))
                    }
                }
            }

            is SettingsMsg.RemoveProfileConfirmed -> _state.update {
                it.copy(
                    activeDialog = SettingsDialog.PasswordDialog,
                    pendingAction = PendingAction.DELETE_PROFILE
                )
            }

            is SettingsMsg.FinalActionConfirmed -> {
                if (state.value.isLoading) return
                _state.update { it.copy(isLoading = true) }
                val password = state.value.confirmPasswordInput
                val action = state.value.pendingAction

                viewModelScope.launch {
                    val result = when (action) {
                        PendingAction.NONE -> Resource.Error(ErrorType.Unknown)
                        PendingAction.DELETE_PROFILE -> settingsInteractor.removeProfile(password)

                        PendingAction.CHANGE_NAME -> {
                            settingsInteractor.changeProfile(
                                ChangeProfileData(
                                    newNickname = state.value.changeNameInput,
                                    currentPassword = password
                                )
                            )
                        }

                        PendingAction.CHANGE_PASSWORD -> {
                            settingsInteractor.changeProfile(
                                ChangeProfileData(
                                    newPassword = state.value.changePasswordInput,
                                    currentPassword = password
                                )
                            )
                        }
                    }
                    _state.update { it.copy(isLoading = false) }
                    when (result) {
                        is Resource.Success -> {
                            if (action == PendingAction.CHANGE_NAME) {
                                _state.update { it.copy(pendingAction = PendingAction.NONE) }
                                settingsInteractor.insertUserName(state.value.changeNameInput)
                            } else {
                                dispatch(SettingsMsg.LogOut)
                            }
                            dispatch(SettingsMsg.DismissDialogs)
                            _effects.send(ShowToast(R.string.success))
                        }

                        is Resource.Error -> dispatch(SettingsMsg.Error(result.errorType))
                    }
                }
            }

            is SettingsMsg.DismissDialogs -> _state.update {
                it.copy(
                    activeDialog = null,
                    confirmPasswordInput = "",
                    changeNameInput = "",
                    changePasswordInput = "",
                    error = null
                )
            }

            is SettingsMsg.Error -> {
                val errorMessage = when (msg.errorType) {
                    ErrorType.NetworkError -> R.string.no_internet
                    ErrorType.ServerError -> R.string.server_error
                    ErrorType.Unauthorized -> R.string.incorrect_credentials
                    else -> R.string.unknown_error
                }
                _state.update { it.copy(error = errorMessage) }
            }

            is SettingsMsg.AvatarPickerClicked -> _state.update { it.copy(isAvatarPickerVisible = true) }
            is SettingsMsg.AvatarPickerDismissed -> _state.update { it.copy(isAvatarPickerVisible = false) }
            is SettingsMsg.AvatarSelected -> viewModelScope.launch {
                settingsInteractor.insertAvatar(msg.avatarKey)
            }
        }
    }
}