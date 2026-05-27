package com.example.diyca.feature.dictionaries.screens.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.ui.coponents.CustomBoxForDictionaries
import com.example.diyca.ui.coponents.CustomSectionButton
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun DictionaryScreen(navHostController: NavHostController) {
    val viewModel: DictionaryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val sections = DictionaryButtonItems.all.map { it.type }
    val pagerState = rememberPagerState(
        initialPage = sections.indexOf(state.selectedSection).coerceAtLeast(0),
        pageCount = { sections.size }
    )
    var isProgrammaticScroll by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is DictionaryEffect.NavigateToItem -> {
                    focusManager.clearFocus()
                    navHostController.navigateSafe(
                        ScreenRoutes.DictionaryItemRout(
                            id = effect.item.id,
                            isFavorites = false,
                            type = state.selectedSection
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                if (!isProgrammaticScroll) {
                    val newSection = sections[page]
                    if (state.selectedSection != newSection) {
                        viewModel.dispatch(DictionaryMsg.InternalNavigate(newSection))
                    }
                }
            }
    }

    LaunchedEffect(state.selectedSection) {
        focusManager.clearFocus()
        val targetPage = sections.indexOf(state.selectedSection)
        if (targetPage != -1 && pagerState.currentPage != targetPage) {
            isProgrammaticScroll = true
            pagerState.animateScrollToPage(targetPage)
            isProgrammaticScroll = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        DictionaryTitle(state, viewModel, focusManager)
        DictionarySearch(state, viewModel)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { pageIndex ->
            val currentType = sections[pageIndex]
            val filteredList = when (currentType) {
                DictionaryType.EXPRESSION -> state.filteredExpressions
                DictionaryType.PROVERB -> state.filteredProverbs
                DictionaryType.WORD -> state.filteredWords
                else -> emptyList()
            }
            if (filteredList.isEmpty()) {
                DictionarySearchEmpty()
            } else {
                DictionaryItems(filteredList, viewModel, focusManager)
            }
        }
    }
}

@Composable
fun DictionarySearchEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.nothing_was_found),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun DictionaryTitle(
    state: DictionaryState,
    viewModel: DictionaryViewModel,
    focusManager: FocusManager
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    bottomStart = Dimens.RoundedCorner_20,
                    bottomEnd = Dimens.RoundedCorner_20
                )
            )
            .padding(Dimens.Padding_16)
    ) {
        Text(
            stringResource(R.string.library),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(Dimens.Padding_8)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_12))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(DictionaryButtonItems.all) { _, item ->
                CustomSectionButton(
                    stringResource(item.title),
                    state.selectedSection == item.type,
                    onClick = {
                        focusManager.clearFocus()
                        if (state.selectedSection != item.type) {
                            viewModel.dispatch(DictionaryMsg.InternalNavigate(item.type))
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionarySearch(state: DictionaryState, viewModel: DictionaryViewModel) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = state.currentSearchText,
                onQueryChange = { text ->
                    viewModel.dispatch(DictionaryMsg.SearchText(text, state.selectedSection))
                },
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                placeholder = {
                    Text(
                        stringResource(R.string.search),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = SearchBarDefaults.inputFieldColors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
        },
        expanded = false,
        onExpandedChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Padding_16),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            dividerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(Dimens.RoundedCorner_12),
        windowInsets = WindowInsets(top = Dimens.ZeroSize)
    ) { }
}

@Composable
fun DictionaryItems(
    dictionaryItems: List<DictionaryItem>,
    viewModel: DictionaryViewModel,
    focusManager: FocusManager
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.Padding_16),
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
    ) {
        itemsIndexed(dictionaryItems) { _, item ->
            CustomBoxForDictionaries(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.dispatch(DictionaryMsg.NavigateToItem(item))
                },
                isVoiced = !item.audio.isNullOrEmpty(),
                isFavorites = item.isFavorite,
                title = item.original,
                subtitle = item.translation,
                isTwoLines = item !is DictionaryItem.Word,
                onFavoritesClick = {
                    focusManager.clearFocus()
                    viewModel.dispatch(DictionaryMsg.UpdateFavorite(item))
                },
                onSoundClick = {
                    focusManager.clearFocus()
                },
            )
        }
        item { Spacer(modifier = Modifier.height(Dimens.Padding_8)) }
    }
}