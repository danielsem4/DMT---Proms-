package org.example.hit.heal.home

import LoginScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.ModulesResponse
import kotlinx.coroutines.launch
import org.example.hit.heal.cdt.presentation.CDTLandingScreen
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Green
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.TextWhite
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.HitberScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 *
 */

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinViewModel()
        val features by viewModel.features.collectAsState(initial = emptyList())
        val navigator = LocalNavigator.currentOrThrow
        var showDialog by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            viewModel.loadFeatures()
        }

        BaseScreen(
            title = stringResource(Resources.String.home),
            navigationIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Resources.Icon.logout,
                        contentDescription = stringResource(Resources.String.logout),
                        tint = White
                    )
                }
            }
        ) {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val minMsgHeight = maxHeight / 2

                Column(Modifier.fillMaxSize()) {
                    MessagesSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = minMsgHeight)
                            .padding(16.dp)
                    ) {
                        Text(
                            stringResource(Resources.String.dont_forget),
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(Resources.String.take_pills),
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(features.filter { it.active }) { feature ->
                            FeatureTile(
                                feature = feature,
                                fontSize = 14.sp,
                                onClick = {
                                    navigateTo(feature.module_id, navigator)
                                }
                            )
                        }
                    }
                }
            }
        }

        if (showDialog) {
            BaseYesNoDialog(
                onDismissRequest = { showDialog = false },
                title = "Logout",
                icon = Resources.Icon.logout,
                message = "Are you sure you want to logout?",
                confirmButtonText = "Yes",
                confirmButtonColor = Green,
                onConfirm = {

                    showDialog = false
                    scope.launch {
                        viewModel.logout()
                        navigator.replace(LoginScreen())
                    }
                },
                dismissButtonText = "No",
                dismissButtonColor = Red,
                onDismissButtonClick = { showDialog = false }
            )
        }
    }

    @Composable
    private fun MessagesSection(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = modifier
        ) {
            Column {
                // Header chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(primaryColor)
                ) {
                    Text(
                        text = stringResource(Resources.String.messages),
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = EXTRA_MEDIUM,
                        color = TextWhite,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(Modifier.height(12.dp))
                content()
            }
        }
    }

    @Composable
    private fun FeatureTile(
        feature: ModulesResponse,
        fontSize: TextUnit,
        onClick: () -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 2.dp,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(8.dp)
                .clickable { onClick() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = iconFor(feature.module_id),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = primaryColor
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = labelFor(feature.module_id),
                    fontSize = fontSize,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }

    private fun navigateTo(moduleId: Int, navigator: Navigator) {
        when (moduleId) {
            17 -> navigator.push(CDTLandingScreen())
            15 ->  navigator.push(HitberScreen())

            else -> { }
        }
    }


    @Composable
    private fun iconFor(id: Int) = when (id) {
        3 -> Resources.Icon.document_share
        4 -> Resources.Icon.measurements
        5 -> Resources.Icon.chat
        7 -> Resources.Icon.meds
        8 -> Resources.Icon.activities
        20 -> Resources.Icon.memory
        19 -> Resources.Icon.hitber
        17 -> Resources.Icon.clock
//        21 -> Resources.Icon.orientation
        else -> Resources.Icon.document_share
    }

    @Composable
    private fun labelFor(id: Int) = when (id) {
        3 -> stringResource(Resources.String.document_share)
        4 -> stringResource(Resources.String.measurements)
        5 -> stringResource(Resources.String.chat)
        7 -> stringResource(Resources.String.medications)
        8 -> stringResource(Resources.String.activities)
        20 -> stringResource(Resources.String.memory)
        19 -> stringResource(Resources.String.hitber)
        17 -> stringResource(Resources.String.clockTest)
//        21 -> stringResource(Resources.String.orientation)
        else -> "Unknown"
    }
}