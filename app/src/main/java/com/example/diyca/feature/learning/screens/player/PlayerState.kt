package com.example.diyca.feature.learning.screens.player

data class PlayerState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val isReady: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val error: Boolean = false
) {
    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() / duration else 0f

    val currentPositionFormatted: String get() = formatTime(currentPosition)
    val durationFormatted: String get() = formatTime(duration)

    private fun formatTime(ms: Long): String {
        val s = (ms / 1000).toInt()
        return "%02d:%02d".format(s / 60, s % 60)
    }
}