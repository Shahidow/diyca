package com.example.diyca.feature.learning.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.diyca.R
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(audioUrl: String) {
    val viewModel: PlayerViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(audioUrl) {
        viewModel.dispatch(PlayerMsg.Init(audioUrl))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.RoundedCorner_20))
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.error) {
            Text(
                text = stringResource(R.string.audio_playback_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Row(
            modifier = Modifier.padding(Dimens.Padding_8),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Padding_8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(Dimens.Size_32))
            } else {
                Box(
                    modifier = Modifier
                        .size(Dimens.Size_40)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            when {
                                state.error -> viewModel.dispatch(PlayerMsg.Init(audioUrl))
                                state.isPlaying -> viewModel.dispatch(PlayerMsg.Pause)
                                else -> viewModel.dispatch(PlayerMsg.Play)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when {
                            state.error -> Icons.Default.Refresh
                            state.isPlaying -> Icons.Default.Pause
                            else -> Icons.Default.PlayArrow
                        },
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(Dimens.Size_24)
                    )
                }
            }
            Slider(
                value = state.progress,
                onValueChange = { viewModel.dispatch(PlayerMsg.SeekTo(it)) },
                enabled = state.isReady && state.duration > 0,
                modifier = Modifier.weight(1f),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(Dimens.Size_16)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        sliderState = sliderState,
                        modifier = Modifier.height(Dimens.Size_10),
                        thumbTrackGapSize = Dimens.Size_2,
                        drawStopIndicator = null,
                        colors = SliderDefaults.colors(
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.3f
                            ),
                            disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.1f
                            ),
                        )
                    )
                }
            )
            Text(
                text = "${state.currentPositionFormatted}/${state.durationFormatted}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}