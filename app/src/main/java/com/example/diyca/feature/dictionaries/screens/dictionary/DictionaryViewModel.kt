package com.example.diyca.feature.dictionaries.screens.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyca.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DictionaryViewModel(private val dictionaryInteractor: DictionaryInteractor) : ViewModel() {

    private val _state = MutableStateFlow(DictionaryState())
    val state = _state.asStateFlow()

    private val _effects = Channel<DictionaryEffect> { Channel.BUFFERED }
    val effects = _effects.receiveAsFlow()

    private var dictionaryJob: Job? = null

    init {
        dispatch(DictionaryMsg.LoadData(DictionaryType.WORD))
    }

    fun dispatch(msg: DictionaryMsg) {
        when (msg) {
            is DictionaryMsg.LoadData -> {
                dictionaryJob?.cancel()
                dictionaryJob = viewModelScope.launch {
                    dictionaryInteractor.getDictionary(msg.section).collect {
                        dispatch(DictionaryMsg.DataLoaded(msg.section, it))
                    }
                }
            }

            is DictionaryMsg.DataLoaded -> {
                when (msg.type) {
                    DictionaryType.EXPRESSION -> _state.update {
                        val items = msg.items.filterIsInstance<DictionaryItem.Expression>()
                        it.copy(
                            expressions = items,
                            filteredExpressions = items.filter { item -> item.matches(it.searchExpression) }
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

                    else -> {}
                }
            }

            is DictionaryMsg.SearchText -> {
                when (msg.section) {
                    DictionaryType.EXPRESSION -> _state.update {
                        it.copy(
                            searchExpression = msg.text,
                            currentSearchText = msg.text,
                            filteredExpressions = it.expressions.filter { item -> item.matches(msg.text) }
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

                    else -> {}
                }
            }

            is DictionaryMsg.UpdateFavorite -> {
                viewModelScope.launch {
                    dictionaryInteractor.updateFavoriteItem(msg.item)
                }
            }

            is DictionaryMsg.InternalNavigate -> {
                _state.update {
                    val searchText = when (msg.section) {
                        DictionaryType.WORD -> it.searchWord
                        DictionaryType.EXPRESSION -> it.searchExpression
                        DictionaryType.PROVERB -> it.searchProverb
                        else -> ""
                    }
                    it.copy(selectedSection = msg.section, currentSearchText = searchText)
                }
                viewModelScope.launch {
                    dispatch(DictionaryMsg.LoadData(msg.section))
                }
            }

            is DictionaryMsg.NavigateToItem -> {
                viewModelScope.launch {
                    _effects.send(DictionaryEffect.NavigateToItem(msg.item))
                }
            }
        }
    }
}