package com.example.diyca.feature.learning.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.data.repository.player.AudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val repository: AudioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()

    fun dispatch(msg: PlayerMsg) {
        when (msg) {
            is PlayerMsg.Init -> {
                repository.setOnProgressListener { dispatch(PlayerMsg.OnProgress(it)) }
                repository.setOnDurationListener { dispatch(PlayerMsg.OnDuration(it)) }
                repository.setOnReadyListener { dispatch(PlayerMsg.OnReady) }
                repository.setOnCompletionListener { dispatch(PlayerMsg.OnComplete) }
                repository.setOnErrorListener { dispatch(PlayerMsg.OnError) }
                repository.setOnLoadingListener { dispatch(PlayerMsg.OnLoading(it)) }
                viewModelScope.launch {
                    val result = repository.prepare(msg.url)
                    if (result.isFailure) {
                        dispatch(PlayerMsg.OnError)
                    }
                }
            }
            is PlayerMsg.Play -> {
                if (_state.value.isReady) {
                    repository.play()
                    _state.update { it.copy(isPlaying = true) }
                }
            }
            is PlayerMsg.Pause -> {
                repository.pause()
                _state.update { it.copy(isPlaying = false) }
            }
            is PlayerMsg.Stop -> {
                repository.stop()
                _state.update { it.copy(isPlaying = false, currentPosition = 0L) }
            }
            is PlayerMsg.SeekTo -> {
                val position = (msg.progress * _state.value.duration).toLong()
                repository.seekTo(position)
                _state.update { it.copy(currentPosition = position) }
            }
            is PlayerMsg.OnProgress -> _state.update { it.copy(currentPosition = msg.position) }
            is PlayerMsg.OnDuration -> _state.update { it.copy(duration = msg.duration) }
            is PlayerMsg.OnReady -> _state.update { it.copy(isReady = true, isLoading = false) }
            is PlayerMsg.OnComplete -> dispatch(PlayerMsg.Stop)
            is PlayerMsg.OnError -> _state.update { it.copy(error = true, isLoading = false) }
            is PlayerMsg.OnLoading -> _state.update { it.copy(isLoading = msg.isLoading) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.release()
    }
}