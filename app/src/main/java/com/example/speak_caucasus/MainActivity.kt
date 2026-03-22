package com.example.speak_caucasus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.speak_caucasus.di.dataModule
import com.example.speak_caucasus.di.domainModule
import com.example.speak_caucasus.di.viewModelModule
import com.example.speak_caucasus.feature.learning.screens.tasks.Tasks
import com.example.speak_caucasus.ui.bottom_nav.MainScreen
import com.example.speak_caucasus.ui.theme.Speak_CaucasusTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(
                    listOf(
                        dataModule,
                        domainModule,
                        viewModelModule,
                    )
                )
            }
        }

        setContent {
            Speak_CaucasusTheme {
                MainScreen()
                //Tasks()
            }
        }
    }
}