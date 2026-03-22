package com.example.diyca.feature.home.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.R
import com.example.diyca.domain.home.settings.SettingsInteractor
import com.example.diyca.domain.home.settings.models.ChangeProfileData
import com.example.diyca.domain.home.settings.models.RemoveProfileData
import com.example.diyca.domain.session.SessionManager
import com.example.diyca.util.ErrorType
import com.example.diyca.util.Resource
import com.example.diyca.util.Validator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _effects = Channel<SettingsEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        observeUserData()
        dispatch(SettingsMsg.LoadData)
    }

    private fun observeUserData() {
        viewModelScope.launch {
            val userEmail = settingsInteractor.getUserEmail()
            settingsInteractor.getUserName().collect { newName ->
                _state.update { it.copy(userName = newName, userEmail = userEmail) }
            }
        }
    }

    fun dispatch(msg: SettingsMsg) {
        when (msg) {
            is SettingsMsg.LoadData -> {
                viewModelScope.launch {
                    val userSettings = settingsInteractor.getUserSettings()
                    dispatch(
                        SettingsMsg.DataLoaded(
                            pic = userSettings.pic,
                            targetLanguage = userSettings.targetLanguage,
                            appLanguage = userSettings.appLanguage
                        )
                    )
                }
            }

            is SettingsMsg.DataLoaded -> {
                _state.update {
                    it.copy(
                        pic = msg.pic,
                        targetLanguage = msg.targetLanguage,
                        appLanguage = msg.appLanguage
                    )
                }
            }

            is SettingsMsg.NavigateBack -> {
                viewModelScope.launch {
                    _effects.send(SettingsEffect.NavigateBack)
                }
            }

            // ВЫХОД
            is SettingsMsg.LogOutClicked -> _state.update { it.copy(showLogoutDialog = true) }
            is SettingsMsg.LogOut -> {
                viewModelScope.launch {
                    sessionManager.logout()
                    _effects.send(SettingsEffect.NavigateToLogin)
                }
            }

            // ИЗМЕНЕНИЕ ИМЕНИ
            is SettingsMsg.UserNameChangeClicked -> {
                if (state.value.isLoading) return
                _state.update { it.copy(showChangeNameDialog = true) }
            }

            is SettingsMsg.UserNameChanged -> _state.update { it.copy(changeNameInput = msg.text) }
            is SettingsMsg.UserNameChangeConfirmed -> {
                if (Validator.isValidName(state.value.changeNameInput)) {
                    _state.update {
                        it.copy(
                            showChangeNameDialog = false, showPasswordDialog = true,
                            pendingAction = PendingAction.CHANGE_NAME, error = null
                        )
                    }
                } else {
                    _state.update { it.copy(error = R.string.invalid_name_format) }
                }
            }

            // ИЗМЕНЕНИЕ ПАРОЛЯ
            is SettingsMsg.PassChangeClicked -> {
                if (state.value.isLoading) return
                _state.update { it.copy(showChangePassDialog = true) }
            }

            is SettingsMsg.ChangePassChanged -> _state.update { it.copy(changePasswordInput = msg.password) }
            is SettingsMsg.PassChangeConfirmed -> {
                if (Validator.isValidPassword(state.value.changePasswordInput)) {
                    _state.update {
                        it.copy(
                            showChangePassDialog = false, showPasswordDialog = true,
                            pendingAction = PendingAction.CHANGE_PASSWORD, error = null
                        )
                    }
                } else {
                    _state.update { it.copy(error = R.string.invalid_password_format) }
                }
            }

            // УДАЛЕНИЕ ПРОФИЛЯ
            is SettingsMsg.RemoveProfileClicked -> {
                if (state.value.isLoading) return
                _state.update { it.copy(showDeleteWarningDialog = true) }
            }

            is SettingsMsg.RemoveProfileConfirmed -> _state.update {
                it.copy(
                    showDeleteWarningDialog = false, showPasswordDialog = true,
                    pendingAction = PendingAction.DELETE_PROFILE
                )
            }

            // ОСТАЛЬНОЕ
            is SettingsMsg.ConfirmPasswordChanged -> _state.update { it.copy(confirmPasswordInput = msg.text) }

            is SettingsMsg.FinalActionConfirmed -> {
                if (state.value.isLoading) return
                _state.update { it.copy(isLoading = true) }
                val password = state.value.confirmPasswordInput
                val action = state.value.pendingAction

                viewModelScope.launch {
                    val result = when (action) {
                        PendingAction.NONE -> Resource.Error(ErrorType.Unknown)
                        PendingAction.DELETE_PROFILE -> {
                            val token = sessionManager.getRefreshToken() ?: ""
                            settingsInteractor.removeProfile(RemoveProfileData(password, token))
                        }

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
                            _effects.send(SettingsEffect.ShowToast(R.string.success))
                        }

                        is Resource.Error -> dispatch(SettingsMsg.Error(result.errorType))
                    }
                }
            }

            is SettingsMsg.DismissDialogs -> _state.update {
                it.copy(
                    showLogoutDialog = false,
                    showDeleteWarningDialog = false,
                    showPasswordDialog = false,
                    showChangeNameDialog = false,
                    showChangePassDialog = false,
                    confirmPasswordInput = "",
                    changeNameInput = "",
                    changePasswordInput = "",
                    error = null
                )
            }

            is SettingsMsg.Error -> {
                if (msg.errorType is ErrorType.NotFound) {
                    dispatch(SettingsMsg.LogOut)
                }
                val errorMessage = when (msg.errorType) {
                    ErrorType.NetworkError -> R.string.no_internet
                    ErrorType.ServerError -> R.string.server_error
                    ErrorType.Unauthorized -> R.string.incorrect_credentials
                    else -> R.string.unknown_error
                }
                _state.update { it.copy(error = errorMessage) }
            }
        }
    }
}