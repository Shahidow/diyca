package com.example.diyca.feature.dictionaries.screens.dictionary_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.favorites.FavoritesInteractor
import com.example.diyca.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.diyca.domain.phrasebooks.PhrasebookItemsInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DictionaryItemViewModel(
    private val phrasebookItemsInteractor: PhrasebookItemsInteractor,
    private val dictionaryInteractor: DictionaryInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val _state = MutableStateFlow(DictionaryItemState())
    val state = _state.asStateFlow()

    private val _effects = Channel<DictionaryItemEffect> { Channel.BUFFERED }
    val effects = _effects.receiveAsFlow()

    fun dispatch(msg: DictionaryItemMsg) {
        when (msg) {
            is DictionaryItemMsg.LoadData -> {
                if (_state.value.items.isNotEmpty()) return
                viewModelScope.launch {
                    val flow = if (msg.itemData.isFavorites) {
                        favoritesInteractor.getFavoritesItems(msg.itemData.type)
                    } else if (msg.itemData.parentId != null) {
                        phrasebookItemsInteractor.getPhrasebookItems(msg.itemData.parentId)
                    } else {
                        dictionaryInteractor.getDictionary(msg.itemData.type)
                    }
                    flow.collect { items ->
                        val initialItem = items.firstOrNull { it.id == msg.itemData.id }
                        _state.update {
                            it.copy(currentItem = initialItem)
                        }
                        dispatch(DictionaryItemMsg.DataLoaded(items))
                    }
                }
            }

            is DictionaryItemMsg.DataLoaded -> {
                if (msg.items.isEmpty()) {
                    dispatch(DictionaryItemMsg.CloseClicked)
                } else {
                    _state.update { it.copy(items = msg.items) }
                }
            }

            is DictionaryItemMsg.UpdateFavorite -> {
                viewModelScope.launch {
                    dictionaryInteractor.updateFavoriteItem(msg.item)
                }
            }

            is DictionaryItemMsg.ChangeCurrentItem -> {
                _state.update { it.copy(currentItem = msg.item) }
            }

            DictionaryItemMsg.CloseClicked -> {
                viewModelScope.launch { _effects.send(DictionaryItemEffect.NavigateBack) }
            }
        }
    }
}