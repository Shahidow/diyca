package com.example.speak_caucasus.feature.dictionaries.screens.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speak_caucasus.domain.dictionaries.dictionary.DictionaryInteractor
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class DictionaryViewModel(private val dictionaryInteractor: DictionaryInteractor) : ViewModel() {

    private val _state = MutableStateFlow(DictionaryState())
    val state = _state.asStateFlow()

    private val _effects = Channel<DictionaryEffect> { Channel.BUFFERED }
    val effects = _effects.receiveAsFlow()

    init {
        start()
        dispatch(DictionaryMsg.LoadData(DictionaryButtonItems.all[0]))
    }

    fun start (){
        viewModelScope.launch {
            dictionaryInteractor.setDic()
        }
    }

    fun dispatch(msg: DictionaryMsg) {
        when (msg) {
            is DictionaryMsg.LoadData -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    dictionaryInteractor.getDictionary(msg.section.type).collect {
                        try {
                            dispatch(
                                DictionaryMsg.DataLoaded(
                                    msg.section.type,
                                    it,
                                )
                            )
                        } catch (e: IOException) {
                            TODO()
                        }
                    }
                }
            }

            is DictionaryMsg.DataLoaded -> {
                when (msg.type) {
                    DictionaryType.EXPRESSION -> {
                        _state.update {
                            it.copy(
                                expressions = msg.items.filterIsInstance<DictionaryItem.Expression>()
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

                    else -> TODO()
                }
            }

            is DictionaryMsg.SearchText -> {
                when (msg.section) {
                    DictionaryType.EXPRESSION -> {
                        _state.update {
                            it.copy(
                                searchExpression = msg.text
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

                    else -> TODO()

                }
            }

            is DictionaryMsg.UpdateFavorite -> {
                viewModelScope.launch {
                    dictionaryInteractor.updateFavoriteItem(msg.item)
                }
            }

            is DictionaryMsg.InternalNavigate -> {
                _state.update {
                    it.copy(
                        selectedSection = msg.section.type
                    )
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