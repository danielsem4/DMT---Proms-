package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import androidx.activity.compose.BackHandler
import org.example.hit.heal.core.presentation.Resources.Icon.chicken
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog

@Composable
actual fun RegisterBackHandler(screen: Screen, onPop: () -> Unit) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        BaseYesNoDialog(
                onDismissRequest = { showDialog.value = false },
                icon = chicken,
                title = "אישור חזרה",
                message = "מעבר אחורה יחזור למסך הבית",
                confirmButtonText = "כן",
                onConfirm = {
                    showDialog.value = false
                    onPop()
                },
                dismissButtonText = "לא",
                onDismissButtonClick = { showDialog.value = false }
            )
    }

    if (screen is BackHandler) {
        // Use screen's own handler
        BackHandler {
            val handled = screen.onBackPressed()
            if (!handled) {
                showDialog.value = true
            }
        }
    } else {
        // Default behavior: show dialog
        BackHandler {
            showDialog.value = true
        }
    }
}
