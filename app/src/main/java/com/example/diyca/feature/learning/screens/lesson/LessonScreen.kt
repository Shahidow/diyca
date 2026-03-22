package com.example.diyca.feature.learning.screens.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.theme.Dimens
import com.example.diyca.ui.theme.Green
import com.example.diyca.ui.theme.MintGreen
import com.example.diyca.ui.theme.diycaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LessonScreen(navHostController: NavHostController, lessonRout: ScreenRoutes.LessonRout) {
    val viewModel: LessonViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect{ effect ->
            when(effect){
                is LessonEffect.NavigateBack -> navHostController.popBackStack()
                is LessonEffect.NavigateToSection -> navHostController.navigate(ScreenRoutes.SectionRout)
            }
        }
    }

    diycaTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = Dimens.Padding_16)
                .verticalScroll(scrollState)
        ) {
            LessonTitle(viewModel)
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Box (
                modifier = Modifier.fillMaxWidth().padding(Dimens.Padding_16)
            ) {
                Text(state.lesson?.text ?: "no data")
            }
            Spacer(modifier = Modifier.height(Dimens.Padding_36))
            SectionsList(viewModel)
        }
    }
}

@Composable
fun LessonTitle(viewModel: LessonViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (icon, text) = createRefs()
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top, margin = Dimens.Padding_8)
                    start.linkTo(parent.start)
                }
                .clickable { viewModel.dispatch(LessonMsg.BackClicked) }
        )
        Text(stringResource(R.string.account_settings), modifier = Modifier.constrainAs(text) {
            top.linkTo(icon.top)
            bottom.linkTo(icon.bottom)
            start.linkTo(icon.end, margin = Dimens.Padding_16)
        })
    }
}

@Composable
fun SectionsList(viewModel: LessonViewModel) {
    SectionItem(viewModel)
    Spacer(modifier = Modifier.height(Dimens.Padding_8))
    SectionItem(viewModel)
    Spacer(modifier = Modifier.height(Dimens.Padding_8))
    SectionItem(viewModel)
}

@Composable
fun SectionItem(viewModel: LessonViewModel){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MintGreen,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .clickable { viewModel.dispatch(LessonMsg.StartTasks("1")) }
    ) {
        ConstraintLayout (modifier = Modifier.fillMaxWidth()) {
            val (progressBar, text1, text2) = createRefs()
            CircularProgressIndicator(
                progress = { 0.8f },
                modifier = Modifier.size(24.dp)
                    .constrainAs(progressBar){
                        start.linkTo(parent.start, margin = Dimens.Padding_16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                color = Green,
                trackColor = MintGreen,
                strokeWidth = 4.dp,
            )
            Text("Урок", modifier = Modifier.constrainAs(text1){
                start.linkTo(progressBar.end, margin = Dimens.Padding_16)
                top.linkTo(parent.top, margin = Dimens.Padding_16)
            })
            Text("Фонема тI", modifier = Modifier.constrainAs(text2){
                start.linkTo(progressBar.end, margin = Dimens.Padding_16)
                bottom.linkTo(parent.bottom, margin = Dimens.Padding_16)
                top.linkTo(text1.bottom)
            })
        }

    }
}