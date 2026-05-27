package com.example.diyca.feature.startup.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.startup.StartupInteractor
import com.example.diyca.util.LoadingStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartupViewModel(private val startupInteractor: StartupInteractor) : ViewModel() {
    private val _state = MutableStateFlow(StartupState())
    val state: StateFlow<StartupState> = _state.asStateFlow()

    fun dispatch(msg: StartupMsg) {
        when (msg) {
            is StartupMsg.LoadData -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    startupInteractor.downloadAndSaveAll().collect { status ->
                        when (status) {
                            is LoadingStatus.Error -> _state.update { it.copy(error = status.errorType.toString(), isError = false) } //TODO
                            is LoadingStatus.Success -> _state.update { it.copy(progress = 1f, isError = false) }
                            is LoadingStatus.Progress -> _state.update {
                                it.copy(
                                    progress = status.value,
                                    message = status.message
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}