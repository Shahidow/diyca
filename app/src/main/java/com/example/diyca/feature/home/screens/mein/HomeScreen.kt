package com.example.diyca.feature.home.screens.mein

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.diyca.R
import com.example.diyca.domain.home.models.Reward
import com.example.diyca.domain.home.models.DailyActivity
import com.example.diyca.domain.learning.models.LessonSection
import com.example.diyca.ui.coponents.CustomBoxContainer
import com.example.diyca.ui.coponents.CustomCircularProgress
import com.example.diyca.ui.coponents.CustomDialog
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.PrimaryTeal
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    BackHandler(enabled = true) { viewModel.dispatch(HomeMsg.BackClicked) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeEffect.CloseApp -> (context as? android.app.Activity)?.finishAffinity()
                is HomeEffect.NavigateTo -> navHostController.navigate(effect.route)
                is HomeEffect.ShowToast -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.Padding_16)
                .clickable { viewModel.dispatch(HomeMsg.GoToProfile) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_profile_placeholder),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                stringResource(R.string.hallo_user, state.userName),
                fontSize = Dimens.TextSize_18,
                modifier = Modifier.padding(start = Dimens.Padding_8)
            )
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        state.todayLesson?.let {
            HomeTodayLesson(it, onClick = { viewModel.dispatch(HomeMsg.StartLesson) })
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        Text(
            stringResource(R.string.activity_today),
            fontSize = Dimens.TextSize_20,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = Dimens.Padding_16)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        HomeTodayActivity(
            dailyActivity = state.dailyActivity,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        Text(
            stringResource(R.string.awards),
            fontSize = Dimens.TextSize_20,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = Dimens.Padding_16)
        )
        HomeAwards(state.rewards)
    }

    if (state.showConfirmation) {
        CustomDialog(
            title = stringResource(R.string.exit),
            message = stringResource(R.string.exit_confirmation),
            confirmButtonText = stringResource(R.string.action_yes),
            dismissButtonText = stringResource(R.string.action_cancel),
            onConfirm = {viewModel.dispatch(HomeMsg.ConfirmExit)},
            onDismiss = {viewModel.dispatch(HomeMsg.DismissExitDialog)}
        )
    }
}

@Composable
fun HomeTodayLesson(lesson: LessonSection, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RoundedCornerShape(Dimens.RoundedCorner_16)
            )
            .fillMaxWidth()
            .padding(Dimens.Padding_16)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.your_task_for_today),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = Dimens.TextSize_20,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            Text(
                text = stringResource(R.string.do_theory_and_practice),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            HomeTodayLessonButton(lesson.section, lesson.title, onClick)
        }
    }

}

@Composable
fun HomeTodayLessonButton(section: String, title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .padding(Dimens.Padding_8)
            .clickable { onClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (box, text1, text2) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(box) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(Dimens.Size_32)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                section,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.constrainAs(text1) {
                    start.linkTo(box.end, margin = Dimens.Padding_16)
                    top.linkTo(parent.top)
                })

            Text(
                title,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.constrainAs(text2) {
                    start.linkTo(box.end, margin = Dimens.Padding_16)
                    top.linkTo(text1.bottom)
                })
        }
    }
}

@Composable
fun HomeTodayActivity(dailyActivity: DailyActivity?, viewModel: HomeViewModel) {
    CustomBoxContainer(
        onClick = { viewModel.dispatch(HomeMsg.GoToActivity) },
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background,
        borderColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.Padding_24),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CustomCircularProgress(
                lessonProgress = dailyActivity?.lessonsCompleted?.toFloat() ?: 0f,
                taskProgress = dailyActivity?.tasksCompleted?.toFloat() ?: 0f
            )
            Column {
                HomeActivityItem(MaterialTheme.colorScheme.primary, stringResource(R.string.lessons_completed))
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
                HomeActivityItem(PrimaryTeal, stringResource(R.string.tasks_completed))
            }

        }
    }
}

@Composable
fun HomeActivityItem(
    color: Color,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(10.dp)
                .width(16.dp)
                .border(
                    width = 5.dp,
                    color = color,
                    shape = RoundedCornerShape(5.dp)
                )
        )
        Text(text, modifier = Modifier.padding(start = Dimens.Padding_8))
    }
}

@Composable
fun HomeAwards(rewards: List<Reward>) {
    CustomBoxContainer(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background,
        borderColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.Padding_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            rewards.forEach {
                HomeAwardItem(it)
            }
        }
    }
}

@Composable
fun HomeAwardItem(reward: Reward) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(reward.imageUrl)
            .crossfade(true)
            .placeholder(R.drawable.ic_profile_placeholder) // Заглушка пока грузится
            .error(R.drawable.ic_profile_placeholder)       // Картинка если ошибка
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(Dimens.Padding_8)
    ) {
        Image(
            painter = painter,
            contentDescription = reward.title,
            modifier = Modifier
                .size(Dimens.Size_48) // размер
                .clip(CircleShape) // круглая картинка
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_4))
        Text(
            text = reward.title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium
        )
    }
}