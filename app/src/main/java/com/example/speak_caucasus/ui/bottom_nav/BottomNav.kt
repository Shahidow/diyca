package com.example.speak_caucasus.ui.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.speak_caucasus.ui.theme.Dimens
import com.example.speak_caucasus.ui.theme.Green

@Composable
fun BottomNavigation(
    navController: NavController
) {
    //val context = LocalContext.current
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4,
        BottomItem.Screen5,
    )
    NavigationBar(
        containerColor = Color.Gray
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRout = backStackEntry?.destination?.route
        listItems.forEach { item ->
            NavigationBarItem(
                selected = currentRout == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconId), contentDescription = "Icon")
                },
                /*label = {
                    Text(text = context.getString(item.title), fontSize = Dimens.TextSize_10)
                },*/
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,      // Цвет иконки в активном состоянии
                    unselectedIconColor = Color.White,   // Цвет иконки в неактивном состоянии
                    selectedTextColor = Green,      // Цвет текста в активном состоянии
                    unselectedTextColor = Color.White    // Цвет текста в неактивном состоянии
                )
            )
        }
    }
}