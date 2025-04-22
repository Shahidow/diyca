package com.example.speak_caucasus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.speak_caucasus.di.dataModule
import com.example.speak_caucasus.di.repositoryModule
import com.example.speak_caucasus.di.viewModelModule
import com.example.speak_caucasus.feature.home.screens.profile.ProfileScreen
import com.example.speak_caucasus.feature.learning.screens.lerning.Learning
import com.example.speak_caucasus.feature.start.screens.login.LoginScreen
import com.example.speak_caucasus.ui.bottom_nav.MainScreen
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(
                listOf(
                    dataModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
        setContent {
            Speak_CaucasusTheme {
                MainScreen()
            }
        }
    }
}