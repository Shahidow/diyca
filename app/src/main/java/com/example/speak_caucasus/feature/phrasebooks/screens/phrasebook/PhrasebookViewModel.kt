package com.example.speak_caucasus.feature.phrasebooks.screens.phrasebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.domain.phrasebooks.PhrasebookInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhrasebookViewModel(private val phrasebookInteractor: PhrasebookInteractor) : ViewModel() {

    private val _state = MutableStateFlow(PhrasebookState())
    val state = _state.asStateFlow()

    private val _effects = Channel<PhrasebookEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(PhrasebookMsg.LoadData)
    }

    fun dispatch(msg: PhrasebookMsg) {
        when (msg) {
            is PhrasebookMsg.LoadData -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    phrasebookInteractor.getPhrasebooks().collect {
                        try {
                            dispatch(PhrasebookMsg.DataLoaded(it))
                        } catch (e: Exception) {
                            TODO()
                        }
                    }
                }
            }

            is PhrasebookMsg.DataLoaded -> {
                _state.update { it.copy(
                    isLoading = false,
                    phrasebookList = msg.phrasebookList
                ) }
            }
            is PhrasebookMsg.PhrasebookOpen -> {
                TODO()
            }
        }
    }
}