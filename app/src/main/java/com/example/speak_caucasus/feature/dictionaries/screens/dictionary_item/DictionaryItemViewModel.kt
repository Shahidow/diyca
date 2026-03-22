package com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.domain.favorites.FavoritesInteractor
import com.example.speak_caucasus.domain.dictionaries.dictionary.DictionaryInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class DictionaryItemViewModel(
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
                viewModelScope.launch {
                    val flow = if (msg.itemData.isFavorites) {
                        favoritesInteractor.getFavoritesItems(msg.itemData.type)
                    } else {
                        dictionaryInteractor.getDictionary(msg.itemData.type)
                    }
                    try {
                        flow.collect { items ->
                            val initialItem = items.firstOrNull { it.id == msg.itemData.id }
                            _state.update {
                                it.copy(currentItem = initialItem)
                            }
                            dispatch(DictionaryItemMsg.DataLoaded(items))
                        }
                    } catch (e: IOException) {
                        // TODO: handle error
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