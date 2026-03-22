package com.example.diyca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.diyca.di.dataModule
import com.example.diyca.di.domainModule
import com.example.diyca.di.viewModelModule
import com.example.diyca.feature.learning.screens.tasks.Tasks
import com.example.diyca.ui.bottom_nav.MainScreen
import com.example.diyca.ui.theme.diycaTheme
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
            diycaTheme {
                MainScreen()
                //Tasks()
            }
        }
    }
}