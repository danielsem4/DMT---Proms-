package core.utils

import androidx.compose.runtime.Composable

@Composable
actual fun BackPressHandler(onBackPressed: () -> Unit) {
    // ב-iOS אין back פיזי, אז לא עושים כלום פה
}