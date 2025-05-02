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
import entryScreen.EntryScreen
import org.example.hit.heal.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin {
            androidLogger() // provides logger to use in android module
            androidContext(this@MainActivity)
        }
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
            App(context = this)
        }
    }
}

@Preview(
    name = "Tablet Portrait Preview",
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun TabletPortraitEntryScreenPreview() {
    EntryScreen().Content()
}