package com.example.diyca.feature.learning.screens.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.domain.learning.models.Lesson
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.coponents.CustomErrorBox
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopicScreen(navHostController: NavHostController, topicRout: ScreenRoutes.TopicRout) {
    val viewModel: TopicViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(topicRout) {
        viewModel.dispatch(TopicMsg.LoadTopic(topicRout))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is TopicEffect.NavigateBack -> navHostController.popBackStackSafe()
                is TopicEffect.NavigateToLesson -> {
                    val lesson = effect.lesson
                    navHostController.navigateSafe(
                        ScreenRoutes.LessonRout(
                            id = lesson.id,
                            topicId = state.topicId,
                            topicTasksCount = state.topicTasksCount,
                            number = lesson.number,
                            title = lesson.title,
                            text = lesson.text,
                            image = lesson.image,
                            audio = lesson.audio,
                            lessonTasksCount = lesson.tasksCount,
                        )
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        TopicHeader(viewModel, state.title)
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
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

            state.error != null -> state.error?.let { error ->
                CustomErrorBox(
                    onClick = { viewModel.dispatch(TopicMsg.LoadData) },
                    errorType = error,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
                ) {
                    item {
                        RichText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.Padding_16)
                        ) {
                            Markdown(content = state.text)
                        }
                    }
                    item { Spacer(modifier = Modifier.height(Dimens.Padding_36)) }
                    items(state.lessons) { lesson ->
                        TopicLessonItem(lesson, viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(Dimens.Padding_36)) }
                }
            }
        }
    }
}

@Composable
fun TopicHeader(viewModel: TopicViewModel, title: String) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon, text) = createRefs()
        CustomBackButton(
            onClick = { viewModel.dispatch(TopicMsg.BackClicked) },
            modifier = Modifier.constrainAs(icon) {
                top.linkTo(parent.top, margin = Dimens.Padding_16)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(icon.top)
                bottom.linkTo(icon.bottom)
                start.linkTo(icon.end, margin = Dimens.Padding_16)
                end.linkTo(parent.end, margin = Dimens.Padding_16)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun TopicLessonItem(lesson: Lesson, viewModel: TopicViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .border(
                width = Dimens.Size_1,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .clickable {
                viewModel.dispatch(
                    TopicMsg.StartLesson(
                        Lesson(
                            id = lesson.id,
                            number = lesson.number,
                            title = lesson.title,
                            text = lesson.text,
                            image = lesson.image,
                            audio = lesson.audio,
                            tasksCount = lesson.tasksCount
                        )
                    )
                )
            }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (progressBar, text1, text2) = createRefs()
            CircularProgressIndicator(
                progress = { lesson.progress },
                modifier = Modifier
                    .size(Dimens.Size_24)
                    .constrainAs(progressBar) {
                        start.linkTo(parent.start, margin = Dimens.Padding_16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = Dimens.Padding_4,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            )
            Text(
                text = stringResource(R.string.lesson_number, lesson.number),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.constrainAs(text1) {
                    start.linkTo(progressBar.end, margin = Dimens.Padding_16)
                    top.linkTo(parent.top, margin = Dimens.Padding_16)
                }
            )
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.constrainAs(text2) {
                    start.linkTo(progressBar.end, margin = Dimens.Padding_16)
                    bottom.linkTo(parent.bottom, margin = Dimens.Padding_16)
                    top.linkTo(text1.bottom)
                }
            )
        }
    }
}