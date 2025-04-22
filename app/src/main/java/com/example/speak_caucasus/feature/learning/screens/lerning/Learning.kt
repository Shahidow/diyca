package com.example.speak_caucasus.feature.learning.screens.lerning

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.speak_caucasus.R
import com.example.speak_caucasus.domain.model.Lessons
import com.example.speak_caucasus.ui.coponents.CustomBoxContainer
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Green
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme


@Composable
@Preview(showBackground = true)
fun Learning() {

    val lessonsList = listOf(
        Lessons(
            id = 123,
            title = "Чеченская азбука",
            lessonsAmount = 21,
            newWordsAmount = 15,
            pic = 1,
            text = "",
            lessonsList = emptyList()
        ),
        Lessons(
            id = 123,
            title = "Урок 2",
            lessonsAmount = 24,
            newWordsAmount = 23,
            pic = 1,
            text = "",
            lessonsList = emptyList()
        )
    )

    Speak_CaucasusTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.Padding_16)
        ) {
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.Padding_16)
            ) {
                Text("План обучения", fontSize = Dimens.TextSize_20)
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
            ) {
                items(lessonsList) { item ->
                    LessonItem(item)
                }
            }
        }
    }
}

@Composable
fun LessonItem(lessons: Lessons) {
    CustomBoxContainer(
        onClick = {

        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)
                .padding(Dimens.Padding_16)
        ) {
            val (title, text1, text2, image) = createRefs()

            Text(
                lessons.title,
                fontSize = Dimens.TextSize_16,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth(0.3f),
                maxLines = 2, // Ограничение на 2 строки
                overflow = TextOverflow.Ellipsis // Добавляет многоточие, если текст не помещается
            )
            val context = LocalContext.current
            Text(
                context.resources.getQuantityString(
                    R.plurals.lesson_count,
                    lessons.lessonsAmount,
                    lessons.lessonsAmount
                ), fontSize = Dimens.TextSize_14,
                modifier = Modifier.constrainAs(text1) {
                    start.linkTo(parent.start)
                    bottom.linkTo(text2.top)
                }
            )
            Text(
                context.resources.getQuantityString(
                    R.plurals.new_words,
                    lessons.newWordsAmount,
                    lessons.newWordsAmount
                ), fontSize = Dimens.TextSize_14,
                modifier = Modifier.constrainAs(text2) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "Lesson Image",
                modifier = Modifier
                    .constrainAs(image) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(text2.end)
                    }
                    .clip(RoundedCornerShape(8.dp))
            )

        }
    }
}