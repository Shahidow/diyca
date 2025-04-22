package com.example.speak_caucasus.ui.bottom_nav

import com.example.speak_caucasus.R

sealed class BottomItem (val title: Int, val iconId: Int, val route: String) {
    data object Screen1: BottomItem(R.string.home, R.drawable.home, ScreenRoutes.HOME_SCREEN)
    data object Screen2: BottomItem(R.string.learning, R.drawable.learning, ScreenRoutes.LEARNING_SCREEN)
    data object Screen3: BottomItem(R.string.phrasebook, R.drawable.phrasebook, ScreenRoutes.PHRASEBOOK_SCREEN)
    data object Screen4: BottomItem(R.string.library, R.drawable.library, ScreenRoutes.LIBRARY_SCREEN)
    data object Screen5: BottomItem(R.string.favorites, R.drawable.favorites, ScreenRoutes.FAVORITES_SCREEN)
}