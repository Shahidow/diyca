package com.example.diyca.ui.bottom_nav

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.diyca.ui.navigation.navigateToTab
import com.example.diyca.ui.theme.Dimens

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val context = LocalContext.current
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4,
        BottomItem.Screen5,
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination
        listItems.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.route::class) ?: false
            val iconSize by animateDpAsState(
                targetValue = if (isSelected) 32.dp else 24.dp,
                animationSpec = tween(durationMillis = 250), label = ""
            )
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigateToTab(item.route)
                },
                icon = {
                    Box(modifier = Modifier.size(iconSize)) {
                        Icon(
                            painter = painterResource(id = item.iconId),
                            contentDescription = "Icon",
                            modifier = Modifier.size(iconSize)
                        )
                    }
                },
                label = {
                    Text(text = context.getString(item.title), fontSize = Dimens.TextSize_10)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}