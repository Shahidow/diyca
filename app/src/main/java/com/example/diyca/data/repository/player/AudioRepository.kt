package com.example.diyca.data.repository.player

interface AudioRepository {
    suspend fun prepare(url: String): Result<Unit>
    fun play()
    fun pause()
    fun stop()
    fun seekTo(position: Long)
    fun release()
    fun getCurrentPosition(): Long
    fun getDuration(): Long
    val isPlaying: Boolean
    val isReady: Boolean

    fun setOnProgressListener(listener: (Long) -> Unit)
    fun setOnDurationListener(listener: (Long) -> Unit)
    fun setOnCompletionListener(listener: () -> Unit)
    fun setOnErrorListener(listener: (String) -> Unit)
    fun setOnReadyListener(listener: () -> Unit)
    fun setOnLoadingListener(listener: (Boolean) -> Unit)
}