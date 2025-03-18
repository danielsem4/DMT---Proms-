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
import org.example.hit.heal.hitber.di.initKoin
import org.example.hit.heal.hitber.understanding.UnderstandingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin()


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

@Preview(
    name = "Tablet Preview",
    showBackground = true,
    widthDp = 900,
    heightDp = 600
)
@Composable
fun AppTabletPreview() {
    //EntryScreen().Content()
    //TimeAndPlace().Content()
    // ShapeScreen().Content()
   // ActionShapesScreen().Content()
    //ConcentrationScreen().Content()
    //NamingScreen().Content()
    //RepetitionScreen().Content()
    UnderstandingScreen().Content()
    //DragAndDropScreen().Content()
    // WritingScreen().Content()
    // BuildShapeScreen().Content()

}