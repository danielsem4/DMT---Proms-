package org.example.hit.heal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initKoin {
//            androidLogger() // provides logger to use in android module
//            androidContext(this@MainActivity)
//        }
        setContent {

            val darkColor = Color.Transparent.hashCode()
            val lightColor = Color.Transparent.hashCode()

            val inDarkTheme = isSystemInDarkTheme()

            val barStyle = if (!inDarkTheme) SystemBarStyle.dark(darkColor)
            else SystemBarStyle.light(lightColor, lightColor)

            enableEdgeToEdge(statusBarStyle = barStyle, navigationBarStyle = barStyle)
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}