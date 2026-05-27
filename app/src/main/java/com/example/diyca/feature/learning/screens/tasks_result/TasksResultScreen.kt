package com.example.diyca.feature.learning.screens.tasks_result

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.coponents.CustomBoxIconButton
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomErrorBox
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateAndPopSelf
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Green
import com.example.diyca.ui.theme.RubyRed
import com.example.diyca.util.ErrorType
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksResultScreen(
    navHostController: NavHostController,
    tasksResultRout: ScreenRoutes.TasksResultRout
) {
    val viewModel: TasksResultViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val currentError = state.error

    LaunchedEffect(tasksResultRout) {
        viewModel.dispatch(TasksResultMsg.LoadTasksResult(tasksResultRout))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TasksResultEffect.CloseTasksResult -> navHostController.popBackStackSafe()
                is TasksResultEffect.StartTasks -> navHostController.navigateAndPopSelf(
                    ScreenRoutes.TasksRout(
                        topicId = state.topicId,
                        lessonId = state.lessonId,
                        isContinue = effect.isContinue,
                        lessonTasksCount = state.lessonTasksCount
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(Dimens.Padding_16)
            .verticalScroll(rememberScrollState())
    ) {
        CustomBoxIconButton(
            onClick = { viewModel.dispatch(TasksResultMsg.CloseClicked) },
            painter = R.drawable.ic_close,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.weight(0.4f))

        Text(
            text = state.title?.let { stringResource(it) } ?: "",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.weight(0.5f))

        TasksResultProgress(
            progress = state.progress,
            completedTasksCount = state.completedTasks.size,
            failedTasksCount = state.tasksCount - state.completedTasks.size,
        )
        Spacer(modifier = Modifier.weight(1f))

        when {
            currentError != null -> {
                TasksResultErrorBox(errorType = currentError, onClick = {
                    viewModel.dispatch(TasksResultMsg.SetResult)
                })
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
            }

            else -> Spacer(modifier = Modifier.height(Dimens.Padding_56))
        }

        if (state.lessonProgress < 1f) {
            CustomButtonColored(
                onClick = { viewModel.dispatch(TasksResultMsg.StartTasksClicked(true)) },
                text = stringResource(R.string.action_continue),
                isEnabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
        }

        CustomButtonColored(
            onClick = { viewModel.dispatch(TasksResultMsg.StartTasksClicked(false)) },
            text = stringResource(R.string.action_start_over),
            isOutlined = true,
            isEnabled = !state.isLoading
        )
        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Composable
fun TasksResultProgress(
    progress: Float,
    completedTasksCount: Int,
    failedTasksCount: Int,
) {
    val targetProgress = progress.coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "ProgressAnimation"
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Padding_16)
    ) {
        val (
            progressIndicator,
            progressText,
            completedTasks,
            failedTasks
        ) = createRefs()

        CircularProgressIndicator(
            progress = { animatedProgress },
            strokeWidth = Dimens.Size_16,
            color = Green,
            trackColor = RubyRed,
            modifier = Modifier
                .size(Dimens.Size_150)
                .constrainAs(progressIndicator) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(progressText) {
                start.linkTo(progressIndicator.start)
                end.linkTo(progressIndicator.end)
                top.linkTo(progressIndicator.top)
                bottom.linkTo(progressIndicator.bottom)
            }
        )
        TasksResultProgressItem(
            modifier = Modifier.constrainAs(completedTasks) {
                start.linkTo(progressIndicator.end)
                end.linkTo(parent.end)
                top.linkTo(progressIndicator.top)
                bottom.linkTo(progressIndicator.bottom, margin = Dimens.Padding_16)
            },
            color = Green,
            text = completedTasksCount.toString()
        )
        TasksResultProgressItem(
            modifier = Modifier.constrainAs(failedTasks) {
                start.linkTo(completedTasks.start)
                top.linkTo(completedTasks.bottom)
            },
            color = RubyRed,
            text = failedTasksCount.toString()
        )
    }
}

@Composable
fun TasksResultProgressItem(
    modifier: Modifier,
    color: Color,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.Size_16)
                .background(shape = RoundedCornerShape(Dimens.RoundedCorner_16), color = color)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = Dimens.Padding_8)
        )
    }
}

@Composable
fun TasksResultErrorBox(errorType: ErrorType, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .border(
                width = Dimens.Size_1,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .padding(Dimens.Padding_16),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_progress_saved),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            CustomErrorBox(
                onClick = onClick,
                errorType = errorType,
                imageSize = Dimens.Size_56,
            )
        }
    }
}