package com.example.speak_caucasus.feature.root

import androidx.lifecycle.ViewModel
import com.example.speak_caucasus.domain.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(
    sessionManager: SessionManager
) : ViewModel() {

    private val _state =
        MutableStateFlow(AppState(sessionManager.isAuthorized.value))

    val state: StateFlow<AppState> = _state.asStateFlow()
}