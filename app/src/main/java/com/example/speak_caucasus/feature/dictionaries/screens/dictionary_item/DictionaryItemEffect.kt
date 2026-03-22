package com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item


sealed class DictionaryItemEffect {
    data class ShowToast(val message: String) : DictionaryItemEffect()
    data object NavigateBack: DictionaryItemEffect()
}