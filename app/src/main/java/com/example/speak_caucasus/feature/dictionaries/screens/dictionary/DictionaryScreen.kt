package com.example.speak_caucasus.feature.dictionaries.screens.dictionary

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.navigation.ScreenRoutes
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryItem
import com.example.speak_caucasus.domain.dictionaries.dictionary.models.DictionaryType
import com.example.speak_caucasus.ui.coponents.CustomBoxForDictionaries
import com.example.speak_caucasus.ui.coponents.CustomSectionButton
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Grey
import com.example.speak_caucasus.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun DictionaryScreen(navHostController: NavHostController) {
    val viewModel: DictionaryViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is DictionaryEffect.ShowToast -> TODO()
                is DictionaryEffect.NavigateToItem -> navHostController.navigate(
                    ScreenRoutes.DictionaryItemRout(
                        id = effect.item.id,
                        isFavorites = false,
                        type = state.selectedSection
                    ),
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        DictionaryTitle(state, viewModel, context)
        DictionarySearch(state, viewModel)
        when (state.selectedSection) {
            DictionaryType.EXPRESSION -> DictionaryItems(
                state.expressions,
                state,
                viewModel
            )

            DictionaryType.PROVERB -> DictionaryItems(state.proverbs, state, viewModel)
            DictionaryType.WORD -> DictionaryItems(state.words, state, viewModel)
            else -> TODO()
        }
    }
}

@Composable
fun DictionaryTitle(
    state: DictionaryState,
    viewModel: DictionaryViewModel,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                White, shape = RoundedCornerShape(
                    bottomStart = Dimens.RoundedCorner_20,
                    bottomEnd = Dimens.RoundedCorner_20
                )
            )
            .padding(Dimens.Padding_16)
    ) {
        Text(
            stringResource(R.string.library),
            fontSize = Dimens.TextSize_20,
            modifier = Modifier.padding(Dimens.Padding_8)
        )
        Spacer(modifier = Modifier.height(Dimens.Padding_12))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),

            ) {
            itemsIndexed(DictionaryButtonItems.all) { _, item ->
                CustomSectionButton(
                    context.getString(item.title),
                    state.selectedSection == item.type,
                    onClick = {
                        if (state.selectedSection != item.type) {
                            viewModel.dispatch(DictionaryMsg.InternalNavigate(item))
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
    val searchText = when (state.selectedSection) {
        DictionaryType.EXPRESSION -> state.searchExpression
        DictionaryType.PROVERB -> state.searchProverb
        DictionaryType.WORD -> state.searchWord
        else -> TODO()
    }
    SearchBar(
        windowInsets = WindowInsets(top = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Padding_16),
        query = searchText,
        onQueryChange = { text ->
            viewModel.dispatch(DictionaryMsg.SearchText(text, state.selectedSection))
        },
        onSearch = { },
        placeholder = {
            Text(stringResource(R.string.search), color = Grey, fontSize = 14.sp)
        },
        active = false,
        onActiveChange = { },
        colors = SearchBarDefaults.colors(
            containerColor = White
        ),
        shape = RoundedCornerShape(Dimens.RoundedCorner_12),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Grey
            )
        }
    ) {}
}

@Composable
fun DictionaryItems(
    list: List<DictionaryItem>,
    state: DictionaryState,
    viewModel: DictionaryViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.Padding_16),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val searchText = when (state.selectedSection) {
            DictionaryType.EXPRESSION -> state.searchExpression
            DictionaryType.PROVERB -> state.searchProverb
            DictionaryType.WORD -> state.searchWord
            else -> TODO()
        }
        itemsIndexed(list) { _, item ->
            if (item.original.contains(searchText, ignoreCase = true)) {
                CustomBoxForDictionaries(
                    onClick = { viewModel.dispatch(DictionaryMsg.NavigateToItem(item)) },
                    isVoiced = !item.audio.isNullOrEmpty(),
                    isFavorites = item.isFavorite,
                    title = item.original,
                    subtitle = item.translation,
                    isTwoLines = item !is DictionaryItem.Word,
                    onFavoritesClick = {
                        viewModel.dispatch(DictionaryMsg.UpdateFavorite(item))
                    },
                    onSoundClick = { },
                )
            }
        }
    }
}