package org.example.hit.heal.home

import LoginScreen
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
import org.example.hit.heal.core.presentation.Green
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {

        val homeViewModel: HomeViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
        var showDialog by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val features by homeViewModel.features.collectAsState()
        val activeFeatures = features.filter({ feature -> feature.active })

        LaunchedEffect(Unit) {
            homeViewModel.loadFeatures()
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
                val minMsgHeight = maxHeight / 2 //TODO use in MessagesSection

                Column(Modifier.fillMaxSize()) {
                    MessagesSection {
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

                    // Push the feature buttons to the bottom of the screen
                    Spacer(modifier = Modifier.weight(.2f))

                    // Feature buttons layout - using BoxWithConstraints for responsive design
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        val isTablet = maxWidth > 600.dp
                        val fontSize = if (isTablet) 20.sp else 12.sp

                        FlowRow(
                            maxItemsInEachRow = if (isTablet) 8 else 3,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            activeFeatures.forEach { feature ->
                                FeatureTile(feature, fontSize, onClick = {
                                    navigateTo(feature, navigator)
                                })
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
                            homeViewModel.logout()
                            navigator.replace(LoginScreen())
                        }
                    },
                    dismissButtonText = "No",
                    dismissButtonColor = Red,
                    onDismissButtonClick = { showDialog = false }
                )
            }
        }
    }

    @Composable
    private fun FeatureTile(feature: ModulesResponse, fontSize: TextUnit, onClick: () -> Unit) {
        FeatureButton(
            icon = iconFor(feature),
            label = labelFor(feature),
            fontSize = fontSize,
            onClick = onClick
        )
    }

    private fun navigateTo(feature: ModulesResponse, navigator: Navigator) =
        when (feature.module_id) {
            17 -> navigator.push(CDTLandingScreen())

            else -> println("No action for feature: $feature")
        }

    @Composable
    private fun iconFor(feature: ModulesResponse) = when (feature.module_id) {
        3 -> Resources.Icon.document_share
        4 -> Resources.Icon.measurements
        5 -> Resources.Icon.chat
        7 -> Resources.Icon.meds
        8 -> Resources.Icon.activities
        20 -> Resources.Icon.memory
        19 -> Resources.Icon.hitber
        17 -> Resources.Icon.clock
        // Add mappings for other module_ids that should have icons

//        29 -> Resources.Icon.document_report
        // For "ParkinsonStatus" (module_id 1), if it becomes active:
//        1 -> Resources.Icon.status_icon
        else -> {
            println("No icon for feature: $feature")
            Icons.Default.Warning
        }
    }

    @Composable
    private fun labelFor(feature: ModulesResponse) = when (feature.module_id) {
        3 -> stringResource(Resources.String.document_share)
        4 -> stringResource(Resources.String.measurements)
        5 -> stringResource(Resources.String.chat)
        7 -> stringResource(Resources.String.medications)
        8 -> stringResource(Resources.String.activities)
        20 -> stringResource(Resources.String.memory)
        19 -> stringResource(Resources.String.hitber)
        17 -> stringResource(Resources.String.clockTest)

        // For example, for "Parkinson report" (module_id 29):
//        29 -> stringResource(Resources.String.parkinson_report_label)
        // For "ParkinsonStatus" (module_id 1):
//        1 -> stringResource(Resources.String.parkinson_status_label)
        else -> {
            println("No label for feature: $feature")
            feature.module_name
        }
    }
}