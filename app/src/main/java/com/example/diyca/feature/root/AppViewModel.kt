package com.example.diyca.feature.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.session.SessionManager
import com.example.diyca.domain.startup.StartupInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val sessionManager: SessionManager,
    private val startupInteractor: StartupInteractor
) : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state.asStateFlow()

    init { checkInitialStatus() }

    private fun checkInitialStatus() {
        viewModelScope.launch {
            sessionManager.isAuthorized.collect { authStatus ->
                _state.update { it.copy(isAuthorized = authStatus) }
                if (authStatus == null) { sessionManager.validateSession() }
                if (authStatus == true) {
                    viewModelScope.launch {
                        startupInteractor.checkVersion()
                    }
                }
            }
        }
    }
}