package com.example.diyca.ui.coponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.PrimaryTeal
import com.example.diyca.util.DAILY_LESSONS_GOAL
import com.example.diyca.util.DAILY_TASKS_GOAL

@Composable
fun CustomProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = clampedProgress,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "ProgressBarAnimation"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.Size_10)
            .padding(horizontal = Dimens.Padding_4)
            .background(color = color.copy(alpha = 0.2f), RoundedCornerShape(Dimens.RoundedCorner_4))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .background(
                    color = color,
                    shape = RoundedCornerShape(Dimens.RoundedCorner_4)
                )
        )
    }
}

@Composable
fun CustomDoubleCircularProgress(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    lessonsCompleted: Int,
    tasksCompleted: Int,
    isAnimationEnabled: Boolean = true,
    size: Dp = Dimens.Size_100,
) {
    val strokeWidth = size * 0.085f
    val innerCircleSize = size * 0.68f
    val targetLessonProgress = (lessonsCompleted.toFloat() / DAILY_LESSONS_GOAL).coerceIn(0f, 1f)
    val targetTaskProgress = (tasksCompleted.toFloat() / DAILY_TASKS_GOAL).coerceIn(0f, 1f)
    val animatedLessonProgress by animateFloatAsState(
        targetValue = targetLessonProgress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "LessonProgressAnimation"
    )
    val animatedTaskProgress by animateFloatAsState(
        targetValue = targetTaskProgress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "TaskProgressAnimation"
    )
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        CircularProgressIndicator(
            progress = { if (isAnimationEnabled) animatedLessonProgress else targetLessonProgress },
            modifier = Modifier.size(size),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = strokeWidth,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        )
        CircularProgressIndicator(
            progress = { if (isAnimationEnabled) animatedTaskProgress else targetTaskProgress },
            modifier = Modifier.size(innerCircleSize),
            color = PrimaryTeal,
            trackColor = PrimaryTeal.copy(alpha = 0.2f),
            strokeWidth = strokeWidth,
        )
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
    }
}