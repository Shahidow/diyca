package com.example.diyca.feature.learning.screens.lesson

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.diyca.R
import com.example.diyca.feature.learning.screens.player.PlayerScreen
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.coponents.CustomButtonColored
import com.example.diyca.ui.coponents.CustomDialog
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import org.koin.androidx.compose.koinViewModel

@Composable
fun LessonScreen(navHostController: NavHostController, lessonRout: ScreenRoutes.LessonRout) {
    val viewModel: LessonViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(lessonRout) {
        viewModel.dispatch(LessonMsg.LoadLesson(lessonRout))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LessonEffect.NavigateBack -> navHostController.popBackStackSafe()
                is LessonEffect.NavigateToTasks -> navHostController.navigateSafe(
                    ScreenRoutes.TasksRout(
                        topicId = effect.topicId,
                        topicTasksCount = effect.topicTasksCount,
                        lessonId = effect.lessonId,
                        isContinue = effect.isContinue,
                        lessonTasksCount = effect.lessonTasksCount
                    )
                )
            }
        }
    }

    if (state.showConfirmation) {
        CustomDialog(
            title = stringResource(R.string.start_tasks),
            message = stringResource(R.string.start_over_or_continue),
            confirmButtonText = stringResource(R.string.action_continue),
            dismissButtonText = stringResource(R.string.action_start_over),
            onConfirm = { viewModel.dispatch(LessonMsg.StartTasks(true)) },
            onDismiss = { viewModel.dispatch(LessonMsg.StartTasks(false)) },
            onCloseRequest = { viewModel.dispatch(LessonMsg.DismissDialog) },
            isLoading = state.isLoading
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.Padding_16)
    ) {
        LessonHeader(state.number, viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        LessonItem(
            text = state.text,
            title = state.title,
            image = state.image,
            audio = state.audio,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))

        if (state.tasksCount > 0) {
            CustomButtonColored(
                onClick = { viewModel.dispatch(LessonMsg.StartTasksClicked) },
                text = stringResource(R.string.start_tasks),
                isEnabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
        }
    }
}

@Composable
fun LessonHeader(number: Int, viewModel: LessonViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon, text) = createRefs()
        CustomBackButton(
            onClick = { viewModel.dispatch(LessonMsg.BackClicked) },
            modifier = Modifier.constrainAs(icon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = stringResource(R.string.lesson_number, number),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(icon.top)
                bottom.linkTo(icon.bottom)
                start.linkTo(icon.end, margin = Dimens.Padding_16)
            }
        )
    }
}

@Composable
fun LessonItem(text: String, title: String, image: String?, audio: String?, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )
        if (!image.isNullOrEmpty()) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_12))
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        if (!audio.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(Dimens.Padding_12))
            PlayerScreen(audioUrl = audio)
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_12))
        RichText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding_16)
        ) {
            Markdown(content = text)
        }
    }
}