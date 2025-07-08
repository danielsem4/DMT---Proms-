package core.utils

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackPressHandler(onBackPressed: () -> Unit) {
    BackHandler {
        onBackPressed()
    }
}