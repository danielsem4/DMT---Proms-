package org.example.hit.heal.presentation.activities

import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.ActivityItem
import org.example.hit.heal.core.presentation.Resources.Icon.joggingIcon
import org.example.hit.heal.core.presentation.Resources.Icon.yogaIcon
import org.example.hit.heal.core.presentation.Resources.String.login
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.cards.SimpleIconCard
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 *
 */

class ActivitiesScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ActivitiesViewModel = koinViewModel()
        val activityItems by viewModel.activities.collectAsState()

        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }

        BaseScreen(title = stringResource(login)) {
            toastMessage?.let {
                ToastMessage(
                    message = it,
                    type = toastType,
                    alignUp = true,
                    onDismiss = { toastMessage = null }
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacingMd),
                verticalArrangement = Arrangement.spacedBy(spacingMd),
                horizontalArrangement = Arrangement.spacedBy(spacingMd)
            ) {
                items(activityItems, key = { it.id }) { item: ActivityItem ->
                    SimpleIconCard(
                        title = item.name,
                        icon = {
                            Icon(
                                painter = painterResource(setActivityIcon(item.name)),
                                contentDescription = item.name,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = paddingSm),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {

                        },
                    )
                }
            }
        }
    }

    private fun setActivityIcon(name: String): DrawableResource {
        return when (name) {
            "Walk" -> yogaIcon
            "Yoga" -> yogaIcon
            "run" -> joggingIcon
            else -> yogaIcon
        }
    }
}