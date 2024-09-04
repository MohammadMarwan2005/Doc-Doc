package com.alaishat.mohammad.docdoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.alaishat.mohammad.docdoc.screens.MainApp
import com.alaishat.mohammad.docdoc.ui.theme.DocDocTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        actionBar?.hide()
        val keep = mutableStateOf(true)
        val splash = installSplashScreen()

        splash.setKeepOnScreenCondition { keep.value }

        lifecycleScope.launch {
            delay(1500)
            keep.value = false
        }


        setContent {
            DocDocTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    MainApp()
                }
            }
        }
    }
}
