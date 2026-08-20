package com.example.diyca.data.repository.player.impl

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.diyca.data.repository.player.AudioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
class AudioRepositoryImpl(
    private val context: Context,
    private val cache: SimpleCache
) : AudioRepository {

    private var exoPlayer: ExoPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var progressJob: Job? = null

    private var onProgressListener: ((Long) -> Unit)? = null
    private var onDurationListener: ((Long) -> Unit)? = null
    private var onCompletionListener: (() -> Unit)? = null
    private var onErrorListener: ((String) -> Unit)? = null
    private var onReadyListener: (() -> Unit)? = null
    private var onLoadingListener: ((Boolean) -> Unit)? = null

    override suspend fun prepare(url: String): Result<Unit> {
        return try {
            release()
            onLoadingListener?.invoke(true)

            val dataSourceFactory = CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(
                    DefaultHttpDataSource.Factory()
                        .setAllowCrossProtocolRedirects(true)
                        .setConnectTimeoutMs(30_000)
                        .setReadTimeoutMs(30_000)
                )

            exoPlayer = ExoPlayer.Builder(context)
                .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
                .build()
                .apply {
                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            when (state) {
                                Player.STATE_BUFFERING -> onLoadingListener?.invoke(true)
                                Player.STATE_READY -> {
                                    onLoadingListener?.invoke(false)
                                    onReadyListener?.invoke()
                                    onDurationListener?.invoke(duration)
                                    startProgressUpdates()
                                }
                                Player.STATE_ENDED -> {
                                    onCompletionListener?.invoke()
                                    stopProgressUpdates()
                                }
                                else -> Unit
                            }
                        }

                        override fun onPlayerError(error: PlaybackException) {
                            onLoadingListener?.invoke(false)
                            onErrorListener?.invoke(error.message ?: "Неизвестная ошибка")
                        }
                    })
                    setMediaItem(MediaItem.fromUri(url))
                    prepare()
                    playWhenReady = false
                }

            Result.success(Unit)
        } catch (e: Exception) {
            onLoadingListener?.invoke(false)
            Result.failure(e)
        }
    }

    override fun play() { exoPlayer?.play() }
    override fun pause() { exoPlayer?.pause() }
    override fun stop() {
        exoPlayer?.apply { pause(); seekTo(0) }
        stopProgressUpdates()
    }
    override fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
        onProgressListener?.invoke(position)
    }
    override fun release() {
        stopProgressUpdates()
        exoPlayer?.release()
        exoPlayer = null
    }
    override fun getCurrentPosition() = exoPlayer?.currentPosition ?: 0L
    override fun getDuration() = exoPlayer?.duration ?: 0L
    override val isPlaying get() = exoPlayer?.isPlaying ?: false
    override val isReady get() = exoPlayer != null

    override fun setOnProgressListener(listener: (Long) -> Unit) { onProgressListener = listener }
    override fun setOnDurationListener(listener: (Long) -> Unit) { onDurationListener = listener }
    override fun setOnCompletionListener(listener: () -> Unit) { onCompletionListener = listener }
    override fun setOnErrorListener(listener: (String) -> Unit) { onErrorListener = listener }
    override fun setOnReadyListener(listener: () -> Unit) { onReadyListener = listener }
    override fun setOnLoadingListener(listener: (Boolean) -> Unit) { onLoadingListener = listener }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (true) {
                if (exoPlayer?.isPlaying == true) {
                    onProgressListener?.invoke(exoPlayer!!.currentPosition)
                }
                delay(500)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }
}