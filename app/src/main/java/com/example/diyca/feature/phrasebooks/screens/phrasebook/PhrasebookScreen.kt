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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.diyca.R
import com.example.diyca.domain.phrasebooks.models.Phrasebook
import com.example.diyca.ui.coponents.CustomBoxForSections
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhrasebookScreen(navHostController: NavHostController) {
    val viewModel: PhrasebookViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhrasebookEffect.NavigateToPhrasebook -> navHostController.navigateSafe(
                    ScreenRoutes.PhrasebookItemsRout(effect.id)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = Dimens.Padding_16)
    ) {
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding_16)
        ) {
            Text(
                text = stringResource(R.string.phrasebook),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.ic_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            items(state.phrasebookList) { item ->
                PhrasebookItemBox(item, viewModel)
            }
            item { Spacer(modifier = Modifier.height(Dimens.Padding_8)) }
        }
        Spacer(modifier = Modifier.height(Dimens.Padding_8))
    }

}

@Composable
fun PhrasebookItemBox(
    phrasebook: Phrasebook, viewModel: PhrasebookViewModel
) {
    val context = LocalContext.current
    var isError by remember { mutableStateOf(false) }

    CustomBoxForSections(
        onClick = { viewModel.dispatch(PhrasebookMsg.PhrasebookOpen(phrasebook.id)) }
    )
    {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Size_32)
        ) {
            val (text, image) = createRefs()
            Box(
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.value(Dimens.Size_28)
                        width = Dimension.value(Dimens.Size_28)
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(phrasebook.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    onState = { state ->
                        isError =
                            state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillHeight,
                )

                if (isError) {
                    Icon(
                        painter = painterResource(R.drawable.ic_placeholder_book),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Text(
                phrasebook.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
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

