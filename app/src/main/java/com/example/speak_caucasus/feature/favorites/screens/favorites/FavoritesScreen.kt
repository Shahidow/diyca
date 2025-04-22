package com.example.speak_caucasus.feature.favorites.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speak_caucasus.ui.coponents.CustomSectionButton
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Grey
import com.example.speak_caucasus.ui.theme.SoftMint
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme
import com.example.speak_caucasus.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun FavoritesScreen() {
    Speak_CaucasusTheme {
        var section by remember { mutableStateOf(FavoritesButtonItems.all[0]) }
        var searchText by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SoftMint)
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
                    "Избранное",
                    fontSize = Dimens.TextSize_20,
                    modifier = Modifier.padding(Dimens.Padding_8)
                )
                Spacer(modifier = Modifier.height(Dimens.Padding_12))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),

                ) {
                    itemsIndexed(FavoritesButtonItems.all) { _, item ->
                        CustomSectionButton(
                            item.title,
                            section == item,
                            onClick = {
                                section = item
                            }
                        )
                    }
                }
            }
            SearchBar(
                windowInsets = WindowInsets(top = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.Padding_16),
                query = searchText,
                onQueryChange = { text ->
                    searchText = text
                },
                onSearch = { },
                placeholder = {
                    Text("Поиск", color = Grey, fontSize = 14.sp)
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
                        contentDescription = "Иконка поиска",
                        tint = Grey
                    )
                }
            ) { }
        }
    }
}