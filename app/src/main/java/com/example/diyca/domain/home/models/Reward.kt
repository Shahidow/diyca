package com.example.diyca.domain.home.models

data class Reward(
    val id: String,
    val title: String,
    val category: String,
    val name: String,
    val image: String?,
    val isOpen: Boolean = false
)