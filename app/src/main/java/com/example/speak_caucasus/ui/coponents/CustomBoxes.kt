package com.example.speak_caucasus.ui.coponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Grey92

@Composable
fun CustomBoxContainer(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier.fillMaxWidth(),
    color: Color = Color.Transparent,
    borderColor: Color = Grey92,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit,
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable { onClick() }
    } else {
        Modifier
    }
    Box(
        modifier = modifier
            .background(color, shape = RoundedCornerShape(Dimens.RoundedCorner_12))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
            .then(clickableModifier)
            .padding(contentPadding)
    ) {
        content()
    }
}

@Composable
fun CustomBoxTaskButton(
    onClick: () -> Unit = {},
    boxModifier: Modifier = Modifier.clickable { onClick() },
    textModifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Grey92
) {
    Box(
        modifier = boxModifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(Dimens.RoundedCorner_12))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        AutoResizeText(
            text,
            modifier = textModifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Padding_24, vertical = Dimens.Padding_16)
        )
    }
}

@Composable
fun CustomBoxForSections(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.clickable { onClick() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Dimens.RoundedCorner_16)
            )
            .padding(Dimens.Padding_16)
    ) {
        content()
    }
}

@Composable
fun CustomBoxIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    painter: Int
) {
    Box(
        modifier = modifier
            .size(Dimens.Padding_32)
            .clickable { onClick() }
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Dimens.RoundedCorner_8)
            )
            .pointerInput(Unit) { // Это предотвратит перехват касаний Pager'ом
                detectTapGestures { onClick() }
            },
        contentAlignment = Alignment.Center
    )
    {
        Icon(
            painter = painterResource(painter),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun CustomBoxForDictionaries(
    onClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSoundClick: () -> Unit,
    modifier: Modifier = Modifier,
    isVoiced: Boolean = false,
    isTwoLines: Boolean = false,
    isFavorites: Boolean = false,
    title: String = "",
    subtitle: String = ""
) {
    val maxLine: Int = if (isTwoLines) 2 else 1
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Dimens.RoundedCorner_12)
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding_16)
        ) {
            val (icon1, column, icon2) = createRefs()

            if (isVoiced) {
                Icon(
                    painter = painterResource(R.drawable.ic_sound),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .constrainAs(icon1) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable { onSoundClick() }
                )
            }

            Icon(
                painter = if (isFavorites) painterResource(R.drawable.ic_favorites) else painterResource(
                    R.drawable.ic_not_favorites
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .constrainAs(icon2) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable { onFavoritesClick() }
            )

            Column(
                modifier = Modifier
                    .constrainAs(column) {
                        start.linkTo(
                            icon1.end,
                            margin = if (isVoiced) Dimens.Padding_16 else 0.dp
                        )
                        end.linkTo(icon2.start, margin = Dimens.Padding_16)
                        width = Dimension.fillToConstraints
                    }
                    .clickable { onClick() }
            ) {
                Text(
                    title,
                    fontSize = Dimens.TextSize_16,
                    modifier = Modifier.padding(bottom = Dimens.Padding_4),
                    maxLines = maxLine, // Ограничение на 2 строки
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    subtitle,
                    fontSize = Dimens.TextSize_14,
                    maxLines = maxLine, // Ограничение на 2 строки
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}

@Composable
fun ImageBorder(
    image: Int
) {
    Box(
        modifier = Modifier
            .size(Dimens.Size_48) // общий размер "кнопки"
            .clip(CircleShape) // форма круга
            .background(MaterialTheme.colorScheme.surface), // фон внутри круга
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(image),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

