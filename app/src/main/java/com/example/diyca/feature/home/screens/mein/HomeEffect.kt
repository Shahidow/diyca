package com.example.diyca.feature.home.screens.mein


sealed class HomeEffect {
    data object GoToProfile : HomeEffect()
    data object GoToActivity : HomeEffect()
    data object StartLesson : HomeEffect()
    data object CloseApp: HomeEffect()
}