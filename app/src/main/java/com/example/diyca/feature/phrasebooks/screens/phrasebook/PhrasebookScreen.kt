package com.example.diyca.feature.phrasebooks.screens.phrasebook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.diyca.R
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import com.example.diyca.ui.coponents.CustomBoxForSections
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhrasebookScreen() {
    val viewModel: PhrasebookViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhrasebookEffect.NavigateToPhrasebook -> TODO()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding_16)
        ) {
            Text(stringResource(R.string.phrasebook), fontSize = Dimens.TextSize_20)
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        Image(
            painter = painterResource(R.drawable.pasted_20250622_131016_4),
            contentDescription = "Title Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
        ) {
            items(state.phrasebookList){ item ->
                PhrasebookItemBox(item, viewModel)
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
    }

}

@Composable
fun PhrasebookItemBox(
    phrasebook: Phrasebook, viewModel: PhrasebookViewModel
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.ic_google)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED) // Кэш на диск
            .memoryCachePolicy(CachePolicy.ENABLED) // Кэш в памяти
            .build()
    )
    CustomBoxForSections(
        onClick = { viewModel.dispatch(PhrasebookMsg.PhrasebookOpen(phrasebook.id)) }
    )
    {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (text, image) = createRefs()

            Image(
                painter = painter,
                contentDescription = "Lesson Image",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.value(Dimens.Size_28)
                    }
            )
            Text(
                phrasebook.title,
                fontSize = Dimens.TextSize_16,
                fontWeight = FontWeight.Bold,
                maxLines = 2, // Ограничение на 2 строки
                overflow = TextOverflow.Ellipsis, // Добавляет многоточие, если текст не помещается
                modifier = Modifier
                    .padding(start = Dimens.Padding_16)
                    .constrainAs(text) {
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
}

