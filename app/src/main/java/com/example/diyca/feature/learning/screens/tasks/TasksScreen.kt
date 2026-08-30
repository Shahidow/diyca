package com.example.diyca.feature.learning.screens.tasks

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.domain.learning.models.task_type.BuildSentenceTask
import com.example.diyca.domain.learning.models.task_type.BuildWordTask
import com.example.diyca.domain.learning.models.task_type.MultipleChoiceTask
import com.example.diyca.domain.learning.models.task_type.SingleChoiceTask
import com.example.diyca.ui.coponents.CustomBoxTaskButton
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomDialog
import com.example.diyca.ui.coponents.CustomErrorBox
import com.example.diyca.ui.coponents.CustomKeyboard
import com.example.diyca.ui.coponents.CustomProgressBar
import com.example.diyca.ui.coponents.CustomTaskButton
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.coponents.CustomTextField
import com.example.diyca.ui.coponents.blinkingCursor
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateAndPopSelf
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Green
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksScreen(navHostController: NavHostController, tasksRout: ScreenRoutes.TasksRout) {
    val viewModel: TasksViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(tasksRout) {
        viewModel.dispatch(TasksMsg.LoadData(tasksRout))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TasksEffect.CloseTasks -> navHostController.popBackStackSafe()
                is TasksEffect.NavigateToResult -> navHostController.navigateAndPopSelf(
                    ScreenRoutes.TasksResultRout(
                        topicId = effect.topicId,
                        topicTasksCount = effect.topicTasksCount,
                        lessonId = effect.lessonId,
                        completedTasks = effect.completedTasks,
                        tasksCount = effect.tasksCount,
                        lessonTasksCount = effect.lessonTasksCount
                    )
                )
            }
        }
    }

    BackHandler {
        viewModel.dispatch(TasksMsg.CloseClicked)
    }

    if (state.showCloseConfirmation) {
        CustomDialog(
            title = stringResource(R.string.terminate_tasks),
            message = stringResource(R.string.lost_progress_confirmation),
            confirmButtonText = stringResource(R.string.action_terminate),
            dismissButtonText = stringResource(R.string.action_cancel),
            onConfirm = { viewModel.dispatch(TasksMsg.CloseTasks) },
            onDismiss = { viewModel.dispatch(TasksMsg.DismissDialogs) },
        )
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(Dimens.Padding_16)
                        .clickable { viewModel.dispatch(TasksMsg.CloseTasks) },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                state.error?.let { error ->
                    CustomErrorBox(
                        errorType = error,
                        onClick = { viewModel.dispatch(TasksMsg.LoadData(tasksRout)) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        else -> TasksMainContent(state, viewModel)
    }
}

@Composable
fun TasksMainContent(
    state: TasksState,
    viewModel: TasksViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Padding_16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.Padding_8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${state.currentTask + 1}/${state.tasks.size}")
            CustomProgressBar(
                progress = state.progress,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.ic_close),
                modifier = Modifier.clickable { viewModel.dispatch(TasksMsg.CloseClicked) },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_8))

        AnimatedContent(
            targetState = state.currentTask,
            transitionSpec = {
                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> -width } + fadeOut()
                )
            },
            modifier = Modifier.weight(1f),
            label = "TaskTransition"
        ) { targetIndex ->
            val currentAnimatedTask = state.tasks.getOrNull(targetIndex)
            Column {
                Text(
                    text = when (currentAnimatedTask) {
                        is BuildSentenceTask -> stringResource(R.string.translate_sentence)
                        is BuildWordTask -> stringResource(R.string.translate_word)
                        is SingleChoiceTask -> stringResource(R.string.choose_single_translate)
                        is MultipleChoiceTask -> stringResource(R.string.choose_multiple_translate)
                        null -> ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                currentAnimatedTask?.let { animatedTask ->
                    when (animatedTask) {
                        is BuildSentenceTask -> BuildSentenceTaskScreen(
                            modifier = Modifier.fillMaxWidth(),
                            buildSentenceTask = animatedTask,
                            selectedWords = state.selectedWords,
                            viewModel = viewModel,
                            answer = state.answer
                        )

                        is BuildWordTask -> BuildWordTaskScreen(
                            modifier = Modifier.fillMaxWidth(),
                            buildWordTask = animatedTask,
                            selectedLetters = state.selectedLetters,
                            viewModel = viewModel,
                            answer = state.answer
                        )

                        is SingleChoiceTask -> ChooseTranslationTaskScreen(
                            modifier = Modifier.fillMaxWidth(),
                            singleChoiceTask = animatedTask,
                            selectedWord = state.selectedSingleWord,
                            viewModel = viewModel,
                            answer = state.answer
                        )

                        is MultipleChoiceTask -> MultipleChoiceTaskScreen(
                            modifier = Modifier.fillMaxWidth(),
                            multipleChoiceTask = animatedTask,
                            selectedOptions = state.selectedMultipleWords,
                            viewModel = viewModel,
                            answer = state.answer
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_24))

        CustomButtonColored(
            onClick = { viewModel.dispatch(TasksMsg.ActionButtonClicked("")) },
            text = if (state.answer == null) stringResource(R.string.action_check) else stringResource(
                R.string.action_continue
            ),
            height = Dimens.Padding_48,
            isEnabled = !state.isLoading && (state.isAnswerNotEmpty || state.answer != null)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        CustomTextButtonColored(
            stringResource(R.string.action_skip),
            onClick = { viewModel.dispatch(TasksMsg.SkipButtonClicked) })
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
    }
}

@Composable
fun BuildSentenceTaskScreen(
    modifier: Modifier = Modifier,
    buildSentenceTask: BuildSentenceTask,
    selectedWords: List<String>,
    viewModel: TasksViewModel,
    answer: Boolean?
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildSentenceTask.question,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .clip(RoundedCornerShape(Dimens.RoundedCorner_16))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 2.dp,
                    color = when (answer) {
                        true -> Green
                        false -> Red
                        null -> Color.Transparent
                    },
                    shape = RoundedCornerShape(Dimens.RoundedCorner_16)
                )
                .defaultMinSize(minHeight = 162.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            selectedWords.forEach { word ->
                CustomTaskButton(
                    word,
                    onClick = {
                        if (answer == null)
                            viewModel.dispatch(TasksMsg.SelectedWordsChanged(selectedWords - word))
                    }
                )
            }
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .defaultMinSize(minHeight = 162.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            buildSentenceTask.words.forEach { word ->
                CustomTaskButton(
                    word,
                    isSelected = selectedWords.contains(word),
                    onClick = {
                        if (answer == null)
                            viewModel.dispatch(TasksMsg.SelectedWordsChanged(selectedWords + word))
                    },
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Composable
fun BuildWordTaskScreen(
    modifier: Modifier = Modifier,
    buildWordTask: BuildWordTask,
    selectedLetters: List<String>,
    viewModel: TasksViewModel,
    answer: Boolean?
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildWordTask.question,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        CustomTextField(
            value = if (answer == null) selectedLetters.joinToString("") + blinkingCursor()
            else selectedLetters.joinToString(""),
            onValueChange = {},
            isBorder = true,
            readOnly = true,
            borderColor = when (answer) {
                true -> Green
                false -> Red
                null -> MaterialTheme.colorScheme.outline
            },
            showDeleteButton = answer == null,
            onDeleteClick = {
                if (answer == null)
                    viewModel.dispatch(TasksMsg.SelectedLettersChanged(selectedLetters.dropLast(1)))
            }
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_36))
        CustomKeyboard(
            onLetterClick = { letter ->
                if (answer == null)
                    viewModel.dispatch(TasksMsg.SelectedLettersChanged(selectedLetters + letter))
            },
            onSpaceClick = {
                if (answer == null)
                    viewModel.dispatch(TasksMsg.SelectedLettersChanged(selectedLetters + " "))
            }
        )
    }
}

@Composable
fun ChooseTranslationTaskScreen(
    modifier: Modifier = Modifier,
    singleChoiceTask: SingleChoiceTask,
    selectedWord: String,
    viewModel: TasksViewModel,
    answer: Boolean?
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = singleChoiceTask.question,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            singleChoiceTask.options.forEach { word ->
                CustomBoxTaskButton(
                    onClick = {
                        if (answer == null)
                            viewModel.dispatch(TasksMsg.SelectedSingleWordChanged(word))
                    },
                    text = word,
                    backgroundColor = if (word == selectedWord) MaterialTheme.colorScheme.surface else Color.Transparent,
                    borderColor = when (answer) {
                        true -> if (word == selectedWord) Green else MaterialTheme.colorScheme.outline
                        false -> if (word == selectedWord) Red else if (word == singleChoiceTask.correctTranslation) Green else MaterialTheme.colorScheme.outline
                        null -> MaterialTheme.colorScheme.outline
                    }
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
            }
        }
    }
}

@Composable
fun MultipleChoiceTaskScreen(
    modifier: Modifier = Modifier,
    multipleChoiceTask: MultipleChoiceTask,
    selectedOptions: List<String>,
    viewModel: TasksViewModel,
    answer: Boolean?
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = multipleChoiceTask.question,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            multipleChoiceTask.options.forEach { option ->
                val isSelected = selectedOptions.contains(option)
                CustomBoxTaskButton(
                    onClick = {
                        if (answer == null)
                            viewModel.dispatch(TasksMsg.SelectedMultipleWordsChanged(option))
                    },
                    text = option,
                    backgroundColor = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                    borderColor = when (answer) {
                        true -> {
                            if (isSelected) Green else MaterialTheme.colorScheme.outline
                        }

                        false -> {
                            when {
                                multipleChoiceTask.correctTranslation.contains(option) -> Green
                                isSelected -> Red
                                else -> MaterialTheme.colorScheme.outline
                            }
                        }

                        null -> {
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        }
                    }
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
            }
        }
    }
}