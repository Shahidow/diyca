package com.example.diyca.feature.phrasebooks.screens.phrasebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.phrasebooks.PhrasebookInteractor
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
                viewModelScope.launch {
                    phrasebookInteractor.getPhrasebooks().collect {
                        dispatch(PhrasebookMsg.DataLoaded(it))
                    }
                }
            }

            is PhrasebookMsg.DataLoaded -> {
                _state.update { it.copy(phrasebookList = msg.phrasebookList) }
            }

            is PhrasebookMsg.PhrasebookOpen -> {
                viewModelScope.launch {
                    _effects.send(PhrasebookEffect.NavigateToPhrasebook(msg.id))
                }
            }
        }
    }
}