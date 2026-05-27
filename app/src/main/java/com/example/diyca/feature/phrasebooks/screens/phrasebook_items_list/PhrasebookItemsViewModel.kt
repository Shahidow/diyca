package com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.phrasebooks.PhrasebookItemsInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhrasebookItemsViewModel(
    private val phrasebookItemsInteractor: PhrasebookItemsInteractor
) : ViewModel() {
    private val _state = MutableStateFlow(PhrasebookItemsState())
    val state = _state.asStateFlow()

    private val _effects = Channel<PhrasebookItemsEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: PhrasebookItemsMsg) {
        when (msg) {
            is PhrasebookItemsMsg.LoadData -> {
                viewModelScope.launch {
                    phrasebookItemsInteractor.getPhrasebookItems(msg.phrasebookId).collect { items ->
                        _state.update { it.copy(phrasebookItems = items) }
                    }
                }
            }

            is PhrasebookItemsMsg.NavigateToItem -> {
                viewModelScope.launch {
                    _effects.send(PhrasebookItemsEffect.NavigateToItem(msg.id, msg.parentId))
                }
            }

            is PhrasebookItemsMsg.NavigateBack -> {
                viewModelScope.launch {
                    _effects.send(PhrasebookItemsEffect.NavigateBack)
                }
            }

            is PhrasebookItemsMsg.UpdateFavorite -> {
                viewModelScope.launch {
                    phrasebookItemsInteractor.updateFavoriteItem(msg.item)
                }
            }
        }
    }

}