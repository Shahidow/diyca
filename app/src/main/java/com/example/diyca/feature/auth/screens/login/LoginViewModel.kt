package com.example.diyca.feature.auth.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.R
import com.example.diyca.domain.auth.login.LoginInteractor
import com.example.diyca.util.Resource
import com.example.diyca.domain.auth.models.LoginData
import com.example.diyca.util.ErrorType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.diyca.util.Validator

class LoginViewModel(private val loginInteractor: LoginInteractor) :
    ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effects = Channel<LoginEffect>()
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: LoginMsg) {
        when (msg) {
            is LoginMsg.LoginClicked -> {
                if (state.value.isLoading) return
                val email = msg.email.trim()
                val password = msg.password

                if (email.isEmpty() || password.isEmpty()) {
                    _state.update { it.copy(error = R.string.fill_all_fields) }
                    return
                }

                if (!Validator.isValidEmail(email) || !Validator.isValidPassword(password)) {
                    _state.update { it.copy(error = R.string.incorrect_credentials) }
                    return
                }

                _state.update { it.copy(isLoading = true, error = null) }
                viewModelScope.launch {
                    val data = loginInteractor.login(LoginData(email, password))
                    when (data) {
                        is Resource.Success -> dispatch(LoginMsg.DataLoaded)
                        is Resource.Error -> dispatch(LoginMsg.Error(data.errorType))
                    }
                }
            }

            is LoginMsg.DataLoaded -> {
                _state.update { it.copy(isLoading = false) }
                viewModelScope.launch {
                    _effects.send(LoginEffect.NavigateToHome)
                }
            }

            is LoginMsg.RegisterClicked -> {
                viewModelScope.launch {
                    _effects.send(LoginEffect.NavigateToRegistration)
                }
            }

            is LoginMsg.ForgottenPasswordClicked -> {
                viewModelScope.launch {
                    _effects.send(LoginEffect.NavigateToRecovery)
                }
            }

            is LoginMsg.Error -> {
                val errorMessage = when (msg.errorType) {
                    is ErrorType.NetworkError -> R.string.no_internet
                    is ErrorType.Unauthorized -> R.string.invalid_credentials
                    is ErrorType.ServerError -> R.string.server_error
                    is ErrorType.Forbidden -> R.string.forbidden
                    else -> R.string.unknown_error
                }
                _state.update { it.copy(isLoading = false, error = errorMessage) }
            }

            is LoginMsg.EmailChanged -> _state.update {
                it.copy(email = msg.email)
            }

            is LoginMsg.PasswordChanged -> _state.update {
                it.copy(password = msg.password)
            }
        }
    }
}