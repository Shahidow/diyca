package com.example.speak_caucasus.feature.home.screens.main

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.bottom_nav.ScreenRoutes
import com.example.speak_caucasus.ui.coponents.CustomBoxContainer
import com.example.speak_caucasus.ui.coponents.CustomButtonColored
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Green
import com.example.speak_caucasus.ui.theme.SoftMint
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Speak_CaucasusTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.Padding_16)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.Padding_16)
                    .clickable { navHostController.navigate(ScreenRoutes.PROFILE_SCREEN) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_progile_placeholder),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Text("Привет, Хасипат", modifier = Modifier.padding(start = Dimens.Padding_8))
            }
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Text(
                "Твоё задание на сегодня",
                modifier = Modifier.padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            TodayTask()
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Text(
                "Активность за сегодня",
                modifier = Modifier.padding(start = Dimens.Padding_16)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_8))
            TodayActivity()
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Text(
                "Награды",
                modifier = Modifier.padding(start = Dimens.Padding_16)
            )
        }
    }
}

@Composable
fun TodayTask() {
    CustomBoxContainer {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding_16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Природа")
                Text("12 уроков 20 новых слов")
            }
            Spacer(modifier = Modifier.height(Dimens.Padding_12))
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(102.dp)
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_12))
            CustomButtonColored(onClick = {}, "Пройти урок", height = Dimens.Padding_48)
        }
    }
}

@Composable
fun TodayActivity() {
    CustomBoxContainer {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.Padding_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                ActivityItem(Green, "Пройдено\nуроков")
                Spacer(modifier = Modifier.height(Dimens.Padding_12))
                ActivityItem(Green, "Выполнено\nзаданий")
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 0.7f },
                    modifier = Modifier.size(116.dp),
                    trackColor = SoftMint,
                    strokeWidth = 10.dp,
                )
                CircularProgressIndicator(
                    progress = { 0.6f },
                    modifier = Modifier.size(80.dp),
                    trackColor = SoftMint,
                    strokeWidth = 10.dp,
                )
            }
        }
    }
}

@Composable
fun ActivityItem(
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