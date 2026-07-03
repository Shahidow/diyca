package com.example.diyca.feature.learning.screens.player

sealed class PlayerMsg {
    data class Init(val url: String) : PlayerMsg()
    data object Play : PlayerMsg()
    data object Pause : PlayerMsg()
    data object Stop : PlayerMsg()
    data class SeekTo(val progress: Float) : PlayerMsg()
    data class OnProgress(val position: Long) : PlayerMsg()
    data class OnDuration(val duration: Long) : PlayerMsg()
    data object OnReady : PlayerMsg()
    data object OnComplete : PlayerMsg()
    data object OnError : PlayerMsg()
    data class OnLoading(val isLoading: Boolean) : PlayerMsg()
}