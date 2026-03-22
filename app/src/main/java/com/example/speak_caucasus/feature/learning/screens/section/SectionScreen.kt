package com.example.speak_caucasus.feature.learning.screens.section

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme

@Composable
@Preview(showBackground = true)
fun SectionScreen() {
    Speak_CaucasusTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = Dimens.Padding_16)
        ) {
            SectionTitle()
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            SectionText(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            CustomButtonColored(
                onClick = { },
                text = "Приступить к практике",
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_32))
        }
    }
}

@Composable
fun SectionTitle() {
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
            //.clickable { viewModel.dispatch(SettingsMsg.NavigateBack) }
        )
        Text("Урок 1", modifier = Modifier.constrainAs(text) {
            top.linkTo(icon.top)
            bottom.linkTo(icon.bottom)
            start.linkTo(icon.end, margin = Dimens.Padding_16)
        })
    }
}

@Composable
fun SectionText(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)) {
        Text("Фонетика П1")
        Spacer(modifier = Modifier.height(Dimens.Padding_12))
        Image(
            painter = painterResource(id = R.drawable.lesson_ph),
            contentDescription = "Lesson Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_12))
        Text(
            "ПІ — губно-губная мгновенная смычно-гортанная глухая фонема."
        )
    }
}