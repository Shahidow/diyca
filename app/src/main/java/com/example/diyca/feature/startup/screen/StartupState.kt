package com.example.diyca.feature.startup.screen

data class StartupState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
    val message: String = "",
    val progress: Float = 0f
)
