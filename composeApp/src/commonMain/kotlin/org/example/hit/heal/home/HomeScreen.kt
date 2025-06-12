package org.example.hit.heal.home

import LoginScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.example.hit.heal.cdt.presentation.LandingScreen
import org.example.hit.heal.core.presentation.Black
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen() : Screen {
    @Composable
    override fun Content() {

        val homeViewModel: HomeViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
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

                    if (isTablet) {
                        // Tablet
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(maxWidth / 6),
                            horizontalArrangement = Arrangement.spacedBy(28.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            featureButtons(navigator, isTablet)
                        }
                    } else {
                        //Phone
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(maxWidth / 3),
                            horizontalArrangement = Arrangement.spacedBy(18.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            featureButtons(navigator, isTablet)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    private fun LazyGridScope.featureButtons(navigator: Navigator, isTablet: Boolean) {
        val textSize = if (isTablet) 20.sp else 12.sp

        item {
            FeatureButton(
                icon = Resources.Icon.meds,
                label = stringResource(Resources.String.medications),
                fontSize = textSize,
                onClick = {}
            )
        }
        item {
            FeatureButton(
                icon = Resources.Icon.graph,
                label = stringResource(Resources.String.graphs),
                fontSize = textSize,
                onClick = {}
            )
        }
        item {
            FeatureButton(
                icon = Resources.Icon.activities,
                label = stringResource(Resources.String.activities),
                fontSize = textSize,
                onClick = {}
            )
        }
        item {
            FeatureButton(
                icon = Resources.Icon.clock,
                label = stringResource(Resources.String.clockTest),
                fontSize = textSize,
                onClick = {
                    navigator.push(LandingScreen())
                }
            )
        }
        item {
            FeatureButton(
                icon = Resources.Icon.measurements,
                label = stringResource(Resources.String.measurements),
                fontSize = textSize,
                onClick = {}
            )
        }
    }
}