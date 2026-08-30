package com.example.diyca.domain.rewards.models

data class Reward(
    val id: String,
    val title: String,
    val category: String,
    val name: String,
    val image: String?,
    val threshold: Int,
    val isOpen: Boolean = false
)