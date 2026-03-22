package com.example.diyca.feature.learning.screens.tasks


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diyca.R
import com.example.diyca.domain.learning.models.task_type.BuildSentenceTask
import com.example.diyca.domain.learning.models.task_type.BuildWordTask
import com.example.diyca.domain.learning.models.task_type.ChooseTranslationTask
import com.example.diyca.ui.coponents.CustomBoxTaskButton
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomProgressBar
import com.example.diyca.ui.coponents.CustomTaskButton
import com.example.diyca.ui.coponents.CustomTextButtonColored
import com.example.diyca.ui.coponents.CustomTextField
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Green
import com.example.diyca.ui.theme.Grey92
import org.koin.androidx.compose.koinViewModel

@Composable
fun Tasks() {
    val viewModel: TasksViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val task = state.tasks.getOrNull(state.currentTask)

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TasksEffect.NavigateToResult -> TODO()
            }
        }
    }

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
                progressColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painterResource(R.drawable.ic_close),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_8))

        Text(
            text = when (task) {
                is BuildSentenceTask -> "Переведи предложение"
                is BuildWordTask -> "Переведи слово"
                is ChooseTranslationTask -> "Выбери правильный вариант"
                null -> ""
            },
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.TextSize_18
        )
        task?.let {
            when (it) {
                is BuildSentenceTask -> BuildSentenceTaskScreen(
                    modifier = Modifier.weight(1f),
                    buildSentenceTask = it,
                    selectedWords = state.selectedWords,
                    viewModel = viewModel,
                    answer = state.answer
                )

                is BuildWordTask -> BuildWordTaskScreen(
                    modifier = Modifier.weight(1f),
                    buildWordTask = it,
                    selectedLetters = state.selectedLetters,
                    viewModel = viewModel,
                    answer = state.answer
                )

                is ChooseTranslationTask -> ChooseTranslationTaskScreen(
                    modifier = Modifier.weight(1f),
                    chooseTranslationTask = it,
                    selectedWord = state.selectedWord,
                    viewModel = viewModel,
                    answer = state.answer
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_24))

        CustomButtonColored(
            onClick = { viewModel.dispatch(TasksMsg.ActionButtonClicked("")) },
            text = if (state.answer == null) stringResource(R.string.action_check) else "Продолжить",
            height = Dimens.Padding_48,
            isEnabled = !state.isLoading
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        CustomTextButtonColored(
            stringResource(R.string.action_skip),
            onClick = { viewModel.dispatch(TasksMsg.SkipButtonClicked) })
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
    }
}

@OptIn(ExperimentalLayoutApi::class)
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
                text = buildSentenceTask.sentence,
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
                    width = 2.dp, // Толщина обводки
                    color = when (answer) {
                        true -> Green
                        false -> Color.Red
                        null -> Color.Transparent
                    }, // Цвет обводки
                    shape = RoundedCornerShape(Dimens.RoundedCorner_16) // Скругление углов обводки
                )
                .defaultMinSize(minHeight = 162.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            selectedWords.forEach { word ->
                CustomTaskButton(
                    word,
                    onClick = {
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
                        viewModel.dispatch(TasksMsg.SelectedWordsChanged(selectedWords + word))
                    }
                )
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
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
                text = buildWordTask.word,
                fontSize = Dimens.TextSize_24,
                textAlign = TextAlign.Center
            )
        }
        CustomTextField(
            selectedLetters.joinToString(""),
            onValueChange = {},
            isBorder = true,
            isEnabled = false,
            borderColor = when (answer) {
                true -> Green
                false -> Color.Red
                null -> Color.Transparent
            }
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_36))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .clip(RoundedCornerShape(Dimens.RoundedCorner_16))
                .background(MaterialTheme.colorScheme.surface)
                .defaultMinSize(minHeight = 162.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            buildWordTask.letters.forEach { letter ->
                CustomTaskButton(
                    letter,
                    isSelected = selectedLetters.contains(letter),
                    onClick = {
                        viewModel.dispatch(TasksMsg.SelectedLettersChanged(selectedLetters + letter))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseTranslationTaskScreen(
    modifier: Modifier = Modifier,
    chooseTranslationTask: ChooseTranslationTask,
    selectedWord: String,
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
                text = chooseTranslationTask.word,
                fontSize = Dimens.TextSize_24,
                textAlign = TextAlign.Center
            )
        }
        FlowColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            chooseTranslationTask.options.forEach { word ->
                CustomBoxTaskButton(
                    onClick = { viewModel.dispatch(TasksMsg.SelectedWordChanged(word)) },
                    text = word,
                    backgroundColor = if (word == selectedWord) Grey92 else Color.Transparent,
                    borderColor = when (answer) {
                        true -> if (word == selectedWord) Green else Color.Transparent
                        false -> if (word == selectedWord) Color.Red else if (word == chooseTranslationTask.correctTranslation) Green else Color.Transparent
                        null -> Color.Transparent
                    }
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
            }
        }
    }
}
