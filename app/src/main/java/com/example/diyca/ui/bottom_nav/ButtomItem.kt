package com.example.diyca.ui.bottom_nav

import com.example.diyca.R
import com.example.diyca.ui.navigation.ScreenRoutes

sealed class BottomItem (val title: Int, val iconId: Int, val route: Any) {
    data object ScreenHome: BottomItem(R.string.home, R.drawable.home, ScreenRoutes.HomeRout)
    data object ScreenLearning: BottomItem(R.string.learning, R.drawable.learning, ScreenRoutes.LearningRout)
    data object ScreenPhrasebook: BottomItem(R.string.phrasebook, R.drawable.phrasebook, ScreenRoutes.PhrasebookRout)
    data object ScreenLibrary: BottomItem(R.string.library, R.drawable.library, ScreenRoutes.LibraryRout)
    data object ScreenFavorites: BottomItem(R.string.favorites, R.drawable.favorites, ScreenRoutes.FavoritesRout)
}