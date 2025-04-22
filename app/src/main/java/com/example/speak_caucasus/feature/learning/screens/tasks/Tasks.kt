package com.example.speak_caucasus.feature.learning.screens.tasks


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.coponents.CustomProgressBar
import com.example.speak_caucasus.ui.coponents.CustomTaskButton
import com.example.speak_caucasus.ui.coponents.CustomTextButtonColored
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Green
import com.example.speak_caucasus.ui.theme.SoftMint
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme

@Preview(showBackground = true)
@Composable
fun Tasks() {
    Speak_CaucasusTheme {
        val progress by remember { mutableFloatStateOf(0.3f) }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.Padding_16, vertical = Dimens.Padding_8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("0/20")
                CustomProgressBar(progress, modifier = Modifier.weight(1f))
                Icon(
                    painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            SentenceCollect()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentenceCollect() {
    val words = listOf(
        "лосось",
        "любимое",
        "кукуруза",
        "это",
        "шашлык",
        "блюдо",
        "хлеб",
        "она",
        "кинза",
        "деревянная нога"
    )
    val selectedWords = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Padding_16),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Переведи предложение")
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Мое любимое блюдо это шашлык",
                textAlign = TextAlign.Center
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(162.dp)
                .clip(RoundedCornerShape(Dimens.RoundedCorner_16))
                .background(SoftMint).border(
                    width = 2.dp, // Толщина обводки
                    color = Green, // Цвет обводки
                    shape = RoundedCornerShape(Dimens.RoundedCorner_16) // Скругление углов обводки
                )
                .defaultMinSize(minHeight = 162.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            selectedWords.forEach { text ->
                CustomTaskButton(
                    text,
                    onClick = {
                        selectedWords.remove(text)
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
            words.forEach { text ->
                CustomTaskButton(
                    text,
                    isSelected = selectedWords.contains(text),
                    onClick = {
                        selectedWords.add(text)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_24))
        CustomButtonColored(
            onClick = {},
            "Проверить",
            height = Dimens.Padding_48
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        CustomTextButtonColored("Пропустить", onClick = {})
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
    }
}