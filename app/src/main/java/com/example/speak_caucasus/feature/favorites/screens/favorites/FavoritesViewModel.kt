package com.example.speak_caucasus.feature.favorites.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.domain.favorites.FavoritesInteractor
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    private val _effects = Channel<FavoritesEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(FavoritesMsg.LoadData(DictionaryType.WORD))
    }

    fun dispatch(msg: FavoritesMsg) {
        when (msg) {
            is FavoritesMsg.LoadData -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    favoritesInteractor.getFavoritesItems(msg.section).collect {
                        try {
                            dispatch(FavoritesMsg.DataLoaded(msg.section, it))
                        } catch (e: IOException) {
                            TODO()
                        }
                    }
                }
            }

            is FavoritesMsg.DataLoaded -> {
                when (msg.type) {
                    DictionaryType.EXPRESSION -> {
                        _state.update {
                            it.copy(
                                expressions = msg.items.filterIsInstance<DictionaryItem.Expression>()
                            )
                        }
                    }

                    DictionaryType.PHRASEBOOK -> {
                        _state.update {
                            it.copy(
                                phrasebookItems = msg.items.filterIsInstance<DictionaryItem.PhrasebookItem>()
                            )
                        }
                    }

                    DictionaryType.PROVERB -> {
                        _state.update {
                            it.copy(
                                proverbs = msg.items.filterIsInstance<DictionaryItem.Proverb>()
                            )
                        }
                    }

                    DictionaryType.WORD -> {
                        _state.update {
                            it.copy(
                                words = msg.items.filterIsInstance<DictionaryItem.Word>()
                            )
                        }
                    }
                }
            }

            is FavoritesMsg.DeleteFromFavorites -> {
                viewModelScope.launch {
                    favoritesInteractor.deleteFavoriteItem(msg.item)
                }
            }

            is FavoritesMsg.SearchText -> {
                when (msg.section) {
                    DictionaryType.EXPRESSION -> {
                        _state.update {
                            it.copy(
                                searchExpression = msg.text
                            )
                        }
                    }

                    DictionaryType.PHRASEBOOK -> {
                        _state.update {
                            it.copy(
                                searchConversationItems = msg.text
                            )
                        }
                    }

                    DictionaryType.PROVERB -> {
                        _state.update {
                            it.copy(
                                searchProverb = msg.text
                            )
                        }
                    }

                    DictionaryType.WORD -> {
                        _state.update {
                            it.copy(
                                searchWord = msg.text
                            )
                        }
                    }
                }
            }

            is FavoritesMsg.InternalNavigate -> {
                _state.update { it.copy(selectedSection = msg.section) }
                viewModelScope.launch {
                    dispatch(FavoritesMsg.LoadData(msg.section))
                }
            }

            is FavoritesMsg.NavigateToItem -> {
                viewModelScope.launch {
                    _effects.send(FavoritesEffect.NavigateToItem(msg.item))
                }
            }
        }
    }
}