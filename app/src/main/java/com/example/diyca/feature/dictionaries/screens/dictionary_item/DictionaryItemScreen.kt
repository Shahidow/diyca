package com.example.diyca.feature.dictionaries.screens.dictionary_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.ui.coponents.CustomBoxIconButton
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import org.koin.androidx.compose.koinViewModel

@Composable
fun DictionaryItemScreen(
    navHostController: NavHostController, itemData: ScreenRoutes.DictionaryItemRout
) {
    val viewModel: DictionaryItemViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    var isInitialScrollDone by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.dispatch(DictionaryItemMsg.LoadData(itemData))
        viewModel.effects.collect { effect ->
            when (effect) {
                is DictionaryItemEffect.ShowToast -> {}
                is DictionaryItemEffect.NavigateBack -> navHostController.popBackStackSafe()
            }
        }
    }

    val initialPage = remember(state.items) {
        state.items.indexOfFirst { it.id == itemData.id }.takeIf { it >= 0 } ?: 0
    }

    val pagerState = rememberPagerState(
        initialPage = initialPage, pageCount = { state.items.size })

    LaunchedEffect(state.items) {
        if (state.items.isNotEmpty() && !isInitialScrollDone) {
            val initialPage =
                state.items.indexOfFirst { it.id == itemData.id }.takeIf { it >= 0 } ?: 0
            pagerState.scrollToPage(initialPage)
            isInitialScrollDone = true
        }
    }

    LaunchedEffect(state.items, pagerState.currentPage) {
        state.items.getOrNull(pagerState.currentPage)?.let { item ->
            viewModel.dispatch(DictionaryItemMsg.ChangeCurrentItem(item))
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        val (close, sound, favorite, pager) = createRefs()
        CustomBoxIconButton(
            onClick = { viewModel.dispatch(DictionaryItemMsg.CloseClicked) },
            painter = R.drawable.ic_close,
            modifier = Modifier
                .constrainAs(close) {
                    end.linkTo(parent.end, margin = Dimens.Padding_32)
                    top.linkTo(parent.top, margin = Dimens.Padding_32)
                }
                .zIndex(1f))

        CustomBoxIconButton(
            onClick = { },
            painter = R.drawable.ic_sound,
            modifier = Modifier
                .constrainAs(sound) {
                    start.linkTo(parent.start, margin = Dimens.Padding_32)
                    bottom.linkTo(parent.bottom, margin = Dimens.Padding_48)
                }
                .zIndex(1f))

        CustomBoxIconButton(
            onClick = {
            state.currentItem?.let {
                viewModel.dispatch(DictionaryItemMsg.UpdateFavorite(it))
            }
        },
            painter = if (state.currentItem?.isFavorite == true) R.drawable.ic_favorites else R.drawable.ic_favorites_not,
            tint = if (state.currentItem?.isFavorite == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .constrainAs(favorite) {
                    end.linkTo(parent.end, margin = Dimens.Padding_32)
                    bottom.linkTo(parent.bottom, margin = Dimens.Padding_48)
                }
                .zIndex(1f))

        HorizontalPager(
            state = pagerState,
            pageSpacing = Dimens.Padding_16,
            contentPadding = PaddingValues(horizontal = Dimens.Padding_32),
            modifier = Modifier.constrainAs(pager) {
                top.linkTo(close.bottom)
                bottom.linkTo(favorite.top)
            }) { page -> CardItem(item = state.items[page]) }
    }
}

@Composable
fun CardItem(item: DictionaryItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.Padding_100)
            .shadow(
                elevation = Dimens.Padding_4, shape = RoundedCornerShape(Dimens.RoundedCorner_20)
            )
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Dimens.RoundedCorner_20)
            )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.Padding_16)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.original,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurface)
                    .height(Dimens.Size_2)
            ) { }
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            ProvideTextStyle(
                value = MaterialTheme.typography.displayMedium.copy(
                    color = if (item is DictionaryItem.PhrasebookItem) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontSize = if (item is DictionaryItem.PhrasebookItem) Dimens.TextSize_24 else Dimens.TextSize_16
                )
            ) {
                RichText(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Markdown(content = item.translation)
                }
            }
            if (item is DictionaryItem.PhrasebookItem) {
                Spacer(modifier = Modifier.height(Dimens.Padding_16))
                Text(
                    text = stringResource(R.string.usage_examples),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_8))
                Text(
                    text = item.usingExample,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}