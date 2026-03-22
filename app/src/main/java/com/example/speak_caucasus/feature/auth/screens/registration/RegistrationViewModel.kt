package com.example.speak_caucasus.feature.auth.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.R
import com.example.speak_caucasus.domain.auth.models.RegistrationData
import com.example.speak_caucasus.domain.auth.registration.RegistrationInteractor
import com.example.speak_caucasus.util.ErrorType
import com.example.speak_caucasus.util.Resource
import com.example.speak_caucasus.util.Validator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationInteractor: RegistrationInteractor) :
    ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state.asStateFlow()

    private val _effects = Channel<RegistrationEffect>()
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: RegistrationMsg) {
        when (msg) {
            is RegistrationMsg.RegistrationClicked -> {
                if (state.value.isLoading) return
                val name = msg.name.trim()
                val email = msg.email.trim()
                val password = msg.password

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    _state.update { it.copy(error = R.string.fill_all_fields) }
                    return
                }

                if (!Validator.isValidName(name)) {
                    _state.update { it.copy(error = R.string.invalid_name_format) }
                    return
                }

                if (!Validator.isValidEmail(email)) {
                    _state.update { it.copy(error = R.string.invalid_email_format) }
                    return
                }

                if (!Validator.isValidPassword(password)) {
                    _state.update { it.copy(error = R.string.invalid_password_format) }
                    return
                }

                _state.update { it.copy(isLoading = true, error = null) }
                viewModelScope.launch {
                    val result = registrationInteractor.registration(
                        RegistrationData(name, email, password)
                    )
                    when (result) {
                        is Resource.Success -> _state.update {
                            it.copy(
                                isSuccess = true,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            dispatch(RegistrationMsg.Error(result.errorType))
                        }
                    }
                }
            }

            is RegistrationMsg.Error -> {
                val errorMessage = when (msg.errorType) {
                    is ErrorType.NetworkError -> R.string.no_internet
                    is ErrorType.ServerError -> R.string.server_error
                    is ErrorType.EmailAlreadyExists -> R.string.email_already_exists
                    else -> R.string.unknown_error
                }
                _state.update { it.copy(error = errorMessage, isLoading = false) }
            }

            is RegistrationMsg.LoginClicked -> {
                viewModelScope.launch {
                    _effects.send(RegistrationEffect.NavigateToLogin)
                }
            }

            is RegistrationMsg.NameChanged -> _state.update { it.copy(name = msg.name) }
            is RegistrationMsg.EmailChanged -> _state.update { it.copy(email = msg.email) }
            is RegistrationMsg.PasswordChanged -> _state.update { it.copy(password = msg.password) }
        }
    }
}