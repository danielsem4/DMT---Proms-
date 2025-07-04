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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import org.example.hit.heal.core.presentation.Black
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.White

import org.example.hit.heal.presentaion.screens.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen() : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {

        val homeViewModel: HomeViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val features by homeViewModel.features.collectAsState()

        LaunchedEffect(Unit) {
            homeViewModel.loadFeatures()
        }

        BaseScreen(
            title = stringResource(Resources.String.home),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        homeViewModel.logout()
                        navigator.replace(LoginScreen())
                    }
                }) {
                    Icon(
                        imageVector = Resources.Icon.logout,
                        contentDescription = stringResource(Resources.String.logout),
                        tint = White
                    )
                }
            }
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Using the updated MessagesSection with a distinct header style
                MessagesSection {
                    // Content below the header inside the MessagesSection
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        stringResource(Resources.String.dont_forget),
                        fontSize = 18.sp,
                        color = Black
                    )
                    Text(
                        stringResource(Resources.String.take_pills),
                        fontSize = 18.sp,
                        color = Black
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

                    FlowRow(
                        maxItemsInEachRow = if (isTablet) 8 else 3,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        featureButtons(features, navigator, isTablet)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    private fun featureButtons(
        features: List<ModulesResponse>,
        navigator: Navigator,
        isTablet: Boolean
    ) {
        val fontSize = if (isTablet) 20.sp else 12.sp

        val activeFeatures = features.filter({ feature -> feature.active })
        activeFeatures.forEach { feature ->
            createFeatureButton(
                featureId = feature.module_id,
                navigator = navigator,
                fontSize = fontSize
            )
        }
    }

    @Composable
    fun createFeatureButton(featureId: Int, navigator: Navigator, fontSize: TextUnit) {
        when (featureId) {
            // document share
            3 -> FeatureButton(
                icon = Resources.Icon.document_share,
                label = stringResource(Resources.String.document_share),
                fontSize = fontSize,
                onClick = { /* handle document share click */ }
            )

            // measurements
            4 -> FeatureButton(
                icon = Resources.Icon.measurements,
                label = stringResource(Resources.String.measurements),
                fontSize = fontSize,
                onClick = { /* handle measurements click */ }
            )

            // chat
            5 -> FeatureButton(
                icon = Resources.Icon.chat,
                label = stringResource(Resources.String.chat),
                fontSize = fontSize,
                onClick = { /* handle chat click */ }
            )

            // medications
            7 -> FeatureButton(
                icon = Resources.Icon.meds,
                label = stringResource(Resources.String.medications),
                fontSize = fontSize,
                onClick = { /* handle medications click */ }
            )

            // Activities
            8 -> FeatureButton(
                icon = Resources.Icon.activities,
                label = stringResource(Resources.String.activities),
                fontSize = fontSize,
                onClick = { /* handle activities click */ }
            )

            // Memory
            13 -> FeatureButton(
                icon = Resources.Icon.memory,
                label = stringResource(Resources.String.memory),
                fontSize = fontSize,
                onClick = { /* handle memory click */ }
            )

            // HitBer
            15 -> FeatureButton(
                icon = Resources.Icon.hitber,
                label = stringResource(Resources.String.hitber),
                fontSize = fontSize,
                onClick = { /* handle hitber click */ }
            )

            // Clock
            16 -> FeatureButton(
                icon = Resources.Icon.clock,
                label = stringResource(Resources.String.clockTest),
                fontSize = fontSize,
                onClick = { navigator.push(CDTLandingScreen()) }
            )

            // Unknown module
            else -> {
                Text("Unknown module id: $featureId")
            }
        }
    }
}