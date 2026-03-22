package com.example.speak_caucasus.feature.auth.screens.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.R
import com.example.speak_caucasus.domain.auth.recovery.RecoveryInteractor
import com.example.speak_caucasus.domain.auth.recovery.models.ResetPasswordData
import com.example.speak_caucasus.domain.auth.recovery.models.VerifyResetCodeData
import com.example.speak_caucasus.util.ErrorType
import com.example.speak_caucasus.util.Resource
import com.example.speak_caucasus.util.Validator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecoveryViewModel(private val recoveryInteractor: RecoveryInteractor) : ViewModel() {
    private val _state = MutableStateFlow(RecoveryState())
    val state: StateFlow<RecoveryState> = _state.asStateFlow()

    private val _effects = Channel<RecoveryEffect>()
    val effects = _effects.receiveAsFlow()

    private var timerJob: kotlinx.coroutines.Job? = null

    fun dispatch(msg: RecoveryMsg) {
        when (msg) {
            is RecoveryMsg.ButtonClicked -> {
                val validationError = when (_state.value.screenType) {
                    RecoveryScreenType.PasswordReset -> {
                        if (_state.value.email.isEmpty()) R.string.fill_all_fields
                        else if (!Validator.isValidEmail(_state.value.email)) R.string.invalid_email_format
                        else null
                    }

                    RecoveryScreenType.VerifyResetCode -> {
                        if (_state.value.checkMailCode.isEmpty()) R.string.fill_all_fields else null
                    }

                    RecoveryScreenType.ResetPassword -> {
                        if (_state.value.newPassword.isEmpty()) R.string.fill_all_fields
                        else if (!Validator.isValidPassword(_state.value.newPassword)) R.string.invalid_password_format
                        else null
                    }
                }

                if (validationError != null) {
                    _state.update { it.copy(error = validationError) }
                    return
                }

                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    val stateValue = _state.value
                    val result = when (stateValue.screenType) {
                        RecoveryScreenType.PasswordReset -> recoveryInteractor.requestPasswordReset(stateValue.email)
                        RecoveryScreenType.VerifyResetCode -> recoveryInteractor.verifyResetCode(stateValue.email, stateValue.checkMailCode)
                        RecoveryScreenType.ResetPassword -> recoveryInteractor.resetPassword(
                            ResetPasswordData(
                                email = stateValue.email,
                                verificationToken = stateValue.token,
                                newPassword = stateValue.newPassword
                            )
                        )
                    }

                    when (result) {
                        is Resource.Success -> {
                            _state.update { currentState ->
                                when (currentState.screenType) {
                                    RecoveryScreenType.PasswordReset -> {
                                        startResendTimer()
                                        currentState.copy(
                                            screenType = RecoveryScreenType.VerifyResetCode,
                                            isLoading = false
                                        )
                                    }
                                    RecoveryScreenType.VerifyResetCode -> currentState.copy(
                                        screenType = RecoveryScreenType.ResetPassword,
                                        token = (result.data as? VerifyResetCodeData)?.verificationToken ?: "",
                                        isLoading = false
                                    )
                                    RecoveryScreenType.ResetPassword -> {
                                        viewModelScope.launch { _effects.send(RecoveryEffect.ClickBack) }
                                        currentState.copy(isLoading = false)
                                    }
                                }
                            }
                        }
                        is Resource.Error -> {
                            dispatch(RecoveryMsg.Error(result.errorType))
                        }
                    }
                }
            }

            is RecoveryMsg.Error -> {
                val errorMessage = when (msg.errorType) {
                    is ErrorType.NetworkError -> R.string.no_internet
                    is ErrorType.Unauthorized -> R.string.invalid_credentials
                    is ErrorType.ServerError -> R.string.server_error
                    is ErrorType.Forbidden -> R.string.forbidden
                    is ErrorType.InvalidRequest -> R.string.invalid_request
                    else -> R.string.unknown_error
                }
                _state.update { it.copy(isLoading = false, error = errorMessage) }
            }

            is RecoveryMsg.ResendCodeClicked -> {
                if (_state.value.resendTimer == 0 && !_state.value.isLoading) {
                    viewModelScope.launch {
                        _state.update { it.copy(isLoading = true, error = null) }
                        val result = recoveryInteractor.requestPasswordReset(_state.value.email)
                        when (result) {
                            is Resource.Success -> {
                                _state.update { it.copy(isLoading = false) }
                                startResendTimer()
                            }
                            is Resource.Error -> {
                                dispatch(RecoveryMsg.Error(result.errorType))
                            }
                        }
                    }
                }
            }

            is RecoveryMsg.TextChanged -> _state.update {
                when (msg.screenType) {
                    RecoveryScreenType.PasswordReset -> it.copy(email = msg.text)
                    RecoveryScreenType.VerifyResetCode -> it.copy(checkMailCode = msg.text)
                    RecoveryScreenType.ResetPassword -> it.copy(newPassword = msg.text)
                }
            }

            is RecoveryMsg.ClickBack -> {
                viewModelScope.launch {
                    _effects.send(RecoveryEffect.ClickBack)
                }
            }
        }
    }

    private fun startResendTimer() {
        timerJob?.cancel()
        _state.update { it.copy(resendTimer = 60, isResendEnabled = false) }
        timerJob = viewModelScope.launch {
            while (_state.value.resendTimer > 0) {
                delay(1000)
                _state.update { it.copy(resendTimer = it.resendTimer - 1) }
            }
            _state.update { it.copy(isResendEnabled = true) }
        }
    }
}