package com.example.diyca.feature.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.data.prefs.UserPrefsRepository
import com.example.diyca.domain.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AppViewModel(
    private val sessionManager: SessionManager,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state.asStateFlow()

    init { checkInitialStatus() }

    private fun checkInitialStatus() {
        viewModelScope.launch {
            combine(
                userPrefsRepository.getLibVersionsFlow(),
                sessionManager.isAuthorized
            ) { versions, authStatus ->
                val needsDownload = versions.values.any { it == null }
                AppState(
                    isAuthorized = authStatus,
                    needsDownload = needsDownload
                )
            }.collect { newState ->
                _state.value = newState
                if (newState.needsDownload == false && newState.isAuthorized == null) {
                    sessionManager.validateSession()
                }
            }
        }
    }
}