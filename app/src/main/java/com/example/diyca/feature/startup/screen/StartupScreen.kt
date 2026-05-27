package com.example.diyca.feature.startup.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.diyca.R
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomProgressBar
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartupScreen() {
    val viewModel: StartupViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = Dimens.Padding_16),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_logo_diyca),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.75f)
        )
        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            text = stringResource(R.string.load_data_message),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(0.5f))

        LoadingTextWithDots(
            baseText = if (state.isError) state.error else state.message,
            style = MaterialTheme.typography.labelMedium,
            isDots = !state.isError,
            color = if (state.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = Dimens.Padding_16)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_4))

        CustomProgressBar(
            progress = state.progress,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        CustomButtonColored(
            onClick = { viewModel.dispatch(StartupMsg.LoadData) },
            text = if (state.isError) stringResource(R.string.action_retry) else stringResource(R.string.action_load),
            isEnabled = !state.isLoading
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun LoadingTextWithDots(
    baseText: String,
    modifier: Modifier = Modifier,
    isDots: Boolean = true,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dotsTransition")
    val dotCount by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = 4,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dotCount"
    )
    val dots = ".".repeat(dotCount)
    Text(
        text = if (!isDots || baseText.isEmpty()) baseText else "$baseText$dots",
        modifier = modifier,
        style = style,
        color = color
    )
}