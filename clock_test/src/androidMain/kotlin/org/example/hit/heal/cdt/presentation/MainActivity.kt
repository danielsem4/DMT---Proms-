package org.example.hit.heal.cdt.presentation

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

        setContent {

            val darkColor = Color.Transparent
            val lightColor = Color.Transparent

            val inDarkTheme = isSystemInDarkTheme()

            enableEdgeToEdge(
                statusBarStyle = if (!inDarkTheme) SystemBarStyle.dark(darkColor.hashCode())
                else SystemBarStyle.light(lightColor.hashCode(), lightColor.hashCode()),
                navigationBarStyle = if (!inDarkTheme) SystemBarStyle.dark(darkColor.hashCode())
                else SystemBarStyle.light(lightColor.hashCode(), lightColor.hashCode())
            )
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}