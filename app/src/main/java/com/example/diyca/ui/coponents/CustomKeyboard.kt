package com.example.diyca.ui.coponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diyca.ui.theme.Dimens

@Composable
fun CustomKeyboard(
    onLetterClick: (String) -> Unit,
    onSpaceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val row1 = listOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х")
    val row2 = listOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э")
    val row3 = listOf("Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "Ъ", "Ӏ")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.RoundedCorner_16))
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimens.Padding_8),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        KeyboardRow(row1, onLetterClick)
        KeyboardRow(row2, onLetterClick)
        KeyboardRow(row3, onLetterClick)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            KeyboardButton(
                text = "␣",
                onClick = onSpaceClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun KeyboardRow(
    letters: List<String>,
    onLetterClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
    ) {
        letters.forEach { letter ->
            KeyboardButton(
                text = letter,
                onClick = { onLetterClick(letter) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun KeyboardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Dimens.RoundedCorner_8)
            )
            .padding(vertical = Dimens.Padding_8)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomKeyboardPreview() {
    CustomKeyboard(
        onLetterClick = {},
        onSpaceClick = {}
    )
}