package com.example.speak_caucasus.ui.bottom_nav

import com.example.speak_caucasus.R
import com.example.speak_caucasus.ui.navigation.ScreenRoutes

sealed class BottomItem (val title: Int, val iconId: Int, val route: Any) {
    data object Screen1: BottomItem(R.string.home, R.drawable.home, ScreenRoutes.HomeRout)
    data object Screen2: BottomItem(R.string.learning, R.drawable.learning, ScreenRoutes.LearningRout)
    data object Screen3: BottomItem(R.string.phrasebook, R.drawable.phrasebook, ScreenRoutes.PhrasebookRout)
    data object Screen4: BottomItem(R.string.library, R.drawable.library, ScreenRoutes.LibraryRout)
    data object Screen5: BottomItem(R.string.favorites, R.drawable.favorites, ScreenRoutes.FavoritesRout)
}