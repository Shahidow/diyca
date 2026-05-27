package com.example.diyca.domain.home.settings.models

import com.example.diyca.R

sealed class UserAvatar(val key: String, val resId: Int) {
    companion object {
        const val KEY_TOWER_1 = "avatar_tower_1"
        const val KEY_TOWER_2 = "avatar_tower_2"
        const val KEY_TOWER_3 = "avatar_tower_3"
        const val KEY_WOLF = "avatar_wolf"
        const val KEY_MAN = "avatar_man"
        const val KEY_WOMAN = "avatar_woman"

        const val DEFAULT_KEY = KEY_TOWER_1

        val all = listOf(Tower1, Tower2, Tower3, Wolf, Man, Woman)
        fun fromKey(key: String?): UserAvatar = all.find { it.key == key } ?: Tower1
    }

    data object Tower1 : UserAvatar(KEY_TOWER_1, R.drawable.ic_avatar_towers_1)
    data object Tower2 : UserAvatar(KEY_TOWER_2, R.drawable.ic_avatar_towers_2)
    data object Tower3 : UserAvatar(KEY_TOWER_3, R.drawable.ic_avatar_towers_3)
    data object Wolf : UserAvatar(KEY_WOLF, R.drawable.ic_avatar_wolf)
    data object Man : UserAvatar(KEY_MAN, R.drawable.ic_avatar_man)
    data object Woman : UserAvatar(KEY_WOMAN, R.drawable.ic_avatar_woman)
}