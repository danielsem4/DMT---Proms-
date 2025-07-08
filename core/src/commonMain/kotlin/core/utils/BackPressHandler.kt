package core.utils

import androidx.compose.runtime.Composable

@Composable
expect fun BackPressHandler(onBackPressed: () -> Unit)
