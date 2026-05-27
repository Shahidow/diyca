package com.example.diyca.feature.favorites.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.favorites.FavoritesInteractor
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    private val _effects = Channel<FavoritesEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    private var favoritesJob: Job? = null

    init {
        dispatch(FavoritesMsg.LoadData(DictionaryType.WORD))
    }

    fun dispatch(msg: FavoritesMsg) {
        when (msg) {
            is FavoritesMsg.LoadData -> {
                favoritesJob?.cancel()
                favoritesJob = viewModelScope.launch {
                    favoritesInteractor.getFavoritesItems(msg.section).collect {
                        dispatch(FavoritesMsg.DataLoaded(msg.section, it))
                    }
                }
            }

            is FavoritesMsg.DataLoaded -> {
                when (msg.type) {
                    DictionaryType.EXPRESSION -> _state.update {
                        val items = msg.items.filterIsInstance<DictionaryItem.Expression>()
                        it.copy(
                            expressions = items,
                            filteredExpressions = items.filter { item -> item.matches(it.searchExpression) }
                        )
                    }

                    DictionaryType.PHRASEBOOK -> _state.update {
                        val items = msg.items.filterIsInstance<DictionaryItem.PhrasebookItem>()
                        it.copy(
                            phrasebookItems = items,
                            filteredPhrasebookItems = items.filter { item -> item.matches(it.searchConversationItems) }
                        )
                    }

                    DictionaryType.PROVERB -> _state.update {
                        val items = msg.items.filterIsInstance<DictionaryItem.Proverb>()
                        it.copy(
                            proverbs = items,
                            filteredProverbs = items.filter { item -> item.matches(it.searchProverb) }
                        )
                    }

                    DictionaryType.WORD -> _state.update {
                        val items = msg.items.filterIsInstance<DictionaryItem.Word>()
                        it.copy(
                            words = items,
                            filteredWords = items.filter { item -> item.matches(it.searchWord) }
                        )
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
                    DictionaryType.EXPRESSION -> _state.update {
                        it.copy(
                            searchExpression = msg.text,
                            currentSearchText = msg.text,
                            filteredExpressions = it.expressions.filter { item -> item.matches(msg.text) }
                        )
                    }

                    DictionaryType.PHRASEBOOK -> _state.update {
                        it.copy(
                            searchConversationItems = msg.text,
                            currentSearchText = msg.text,
                            filteredPhrasebookItems = it.phrasebookItems.filter { item ->
                                item.matches(msg.text)
                            }
                        )
                    }

                    DictionaryType.PROVERB -> _state.update {
                        it.copy(
                            searchProverb = msg.text,
                            currentSearchText = msg.text,
                            filteredProverbs = it.proverbs.filter { item -> item.matches(msg.text) }
                        )
                    }

                    DictionaryType.WORD -> _state.update {
                        it.copy(
                            searchWord = msg.text,
                            currentSearchText = msg.text,
                            filteredWords = it.words.filter { item -> item.matches(msg.text) }
                        )
                    }
                }
            }

            is FavoritesMsg.InternalNavigate -> {
                _state.update {
                    val searchText = when (msg.section) {
                        DictionaryType.WORD -> it.searchWord
                        DictionaryType.EXPRESSION -> it.searchExpression
                        DictionaryType.PROVERB -> it.searchProverb
                        DictionaryType.PHRASEBOOK -> it.searchConversationItems
                    }
                    it.copy(selectedSection = msg.section, currentSearchText = searchText)
                }
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