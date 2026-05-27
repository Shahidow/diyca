package com.example.diyca.feature.phrasebooks.screens.phrasebook_items_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.diyca.R
import com.example.diyca.domain.dictionaries.dictionary.models.DictionaryType
import com.example.diyca.ui.coponents.CustomBackButton
import com.example.diyca.ui.coponents.CustomBoxForDictionaries
import com.example.diyca.ui.navigation.ScreenRoutes
import com.example.diyca.ui.navigation.navigateSafe
import com.example.diyca.ui.navigation.popBackStackSafe
import com.example.diyca.ui.theme.Dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhrasebookItemsScreen(
    navHostController: NavHostController,
    parentIdData: ScreenRoutes.PhrasebookItemsRout
) {
    val viewModel: PhrasebookItemsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(parentIdData) {
        viewModel.dispatch(PhrasebookItemsMsg.LoadData(parentIdData.parentId))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhrasebookItemsEffect.NavigateBack -> navHostController.popBackStackSafe()
                is PhrasebookItemsEffect.NavigateToItem -> navHostController.navigateSafe(
                    ScreenRoutes.DictionaryItemRout(
                        id = effect.id,
                        isFavorites = false,
                        type = DictionaryType.PHRASEBOOK,
                        parentId = effect.parentId
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        PhrasebookItemsTitle(viewModel)
        Spacer(modifier = Modifier.height(Dimens.Padding_16))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.Padding_16),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding_8)
        ) {
            itemsIndexed(state.phrasebookItems) { _, item ->
                CustomBoxForDictionaries(
                    onClick = { viewModel.dispatch(PhrasebookItemsMsg.NavigateToItem(item.id, item.parentId)) },
                    onFavoritesClick = { viewModel.dispatch(PhrasebookItemsMsg.UpdateFavorite(item)) },
                    onSoundClick = {},
                    isVoiced = !item.audio.isNullOrEmpty(),
                    isTwoLines = true,
                    isFavorites = item.isFavorite,
                    title = item.original,
                    subtitle = item.translation
                )
            }
        }
    }
}

@Composable
fun PhrasebookItemsTitle(viewModel: PhrasebookItemsViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    bottomStart = Dimens.RoundedCorner_20,
                    bottomEnd = Dimens.RoundedCorner_20
                )
            )
            .padding(Dimens.Padding_16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBackButton(
            onClick = { viewModel.dispatch(PhrasebookItemsMsg.NavigateBack) }
        )
        Spacer(modifier = Modifier.width(Dimens.Padding_16))
        Text(
            stringResource(R.string.library),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(Dimens.Padding_8)
        )
    }
}