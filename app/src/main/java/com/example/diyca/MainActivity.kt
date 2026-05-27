package com.example.diyca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.diyca.di.dataModule
import com.example.diyca.di.domainModule
import com.example.diyca.di.viewModelModule
import com.example.diyca.ui.MainScreen
import com.example.diyca.ui.theme.DiycaTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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

        setContent { DiycaTheme { MainScreen() } }
    }
}