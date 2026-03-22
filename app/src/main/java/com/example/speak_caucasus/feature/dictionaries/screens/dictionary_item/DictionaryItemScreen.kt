package com.example.speak_caucasus.feature.dictionaries.screens.dictionary_item

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.navigation.ScreenRoutes
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.ui.coponents.CustomBoxIconButton
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Grey
import com.example.speak_caucasus.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun DictionaryItemScreen(
    navHostController: NavHostController,
    itemData: ScreenRoutes.DictionaryItemRout
) {
    val viewModel: DictionaryItemViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val favoriteIcon = remember(state.currentItem) {
        derivedStateOf {
            if (state.currentItem?.isFavorite == true)
                R.drawable.ic_favorites
            else
                R.drawable.ic_not_favorites
        }
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch(
            DictionaryItemMsg.LoadData(itemData)
        )
        viewModel.effects.collect { effect ->
            when (effect) {
                is DictionaryItemEffect.ShowToast -> TODO()
                is DictionaryItemEffect.NavigateBack -> navHostController.popBackStack()
            }
        }
    }

    val initialPage = remember(state.items) {
        state.items.indexOfFirst { it.id == itemData.id }.takeIf { it >= 0 } ?: 0
    }

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { state.items.size }
    )

    // скролл к нужной странице после инициализации
    LaunchedEffect(initialPage) {
        if (initialPage != pagerState.currentPage) {
            pagerState.scrollToPage(initialPage)
        }
    }

    // Отслеживание текущей страницы
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
                .zIndex(1f)
        )

        CustomBoxIconButton(
            onClick = { },
            painter = R.drawable.ic_sound,
            modifier = Modifier
                .constrainAs(sound) {
                    start.linkTo(parent.start, margin = Dimens.Padding_32)
                    bottom.linkTo(parent.bottom, margin = Dimens.Padding_48)
                }
                .zIndex(1f)
        )

        CustomBoxIconButton(
            onClick = {
                state.currentItem?.let {
                    viewModel.dispatch(DictionaryItemMsg.UpdateFavorite(it))
                }
            },
            painter = favoriteIcon.value,
            modifier = Modifier
                .constrainAs(favorite) {
                    end.linkTo(parent.end, margin = Dimens.Padding_32)
                    bottom.linkTo(parent.bottom, margin = Dimens.Padding_48)
                }
                .zIndex(1f)
        )

        HorizontalPager(
            state = pagerState,
            pageSpacing = Dimens.Padding_16,
            contentPadding = PaddingValues(horizontal = Dimens.Padding_32),
            modifier = Modifier.constrainAs(pager) {
                top.linkTo(close.bottom)
                bottom.linkTo(favorite.top)
            }
        ) { page -> CardItem(item = state.items[page]) }
    }
}

@Composable
fun CardItem(item: DictionaryItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.Padding_100)
            .shadow(
                elevation = Dimens.Padding_4,
                shape = RoundedCornerShape(Dimens.RoundedCorner_20)
            )
            .background(White, shape = RoundedCornerShape(Dimens.RoundedCorner_20))

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.Padding_16)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.original, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Grey)
                    .height(2.dp)
            ) { }
            Spacer(modifier = Modifier.height(Dimens.Padding_16))
            Text(
                text = item.translation,
                fontSize = 20.sp
            )
        }

    }
}