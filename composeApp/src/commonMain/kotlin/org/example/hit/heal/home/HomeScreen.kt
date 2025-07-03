package org.example.hit.heal.home

import LoginScreen
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.ModulesResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.cdt.presentation.CDTLandingScreen
import org.example.hit.heal.core.presentation.Black
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.TextWhite
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen : Screen {
    @OptIn(ExperimentalLayoutApi::class)
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
            title = stringResource(Resources.String.home), navigationIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Resources.Icon.logout,
                        contentDescription = stringResource(Resources.String.logout),
                        tint = White
                    )
                }
            }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(16.dp)
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
                        stringResource(Resources.String.take_pills), fontSize = 18.sp, color = Black
                    )
                }

                // Push the feature buttons to the bottom of the screen
                Spacer(modifier = Modifier.weight(.2f))

                // Feature buttons layout - using BoxWithConstraints for responsive design
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                ) {
                    val isTablet = maxWidth > 600.dp
                    val fontSize = if (isTablet) 20.sp else 12.sp

                    FlowRow(
                        maxItemsInEachRow = if (isTablet) 8 else 3,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        features.filter { it.active }.mapIndexed { index, feature ->
                            Pair(index, feature)
                        }.forEach { (index, feature) ->
                            FeatureTile(
                                feature = feature,
                                fontSize = fontSize,
                                animationDelay = index * 100L,
                                onClick = {
                                    navigateTo(feature, navigator)
                                })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (showDialog) {
            BaseYesNoDialog(
                onDismissRequest = { showDialog = false },
                title = "Logout",
                icon = Resources.Icon.logout,
                message = "Are you sure you want to logout?",
                onConfirm = {
                    showDialog = false
                    scope.launch {
                        viewModel.logout()
                        navigator.replace(LoginScreen())
                    }
                },
                onDismissButtonClick = { showDialog = false })
        }
    }

    @Composable
    private fun MessagesSection(
        content: @Composable ColumnScope.() -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp).padding(16.dp)
        ) {
            Column {
                // Header chip
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(primaryColor)
                ) {
                    Text(
                        text = stringResource(Resources.String.messages),
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = EXTRA_MEDIUM,
                        color = TextWhite,
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
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
        feature: ModulesResponse, fontSize: TextUnit, animationDelay: Long, onClick: () -> Unit
    ) {
        var animated by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(animationDelay)
            animated = true
        }

        val offsetY by animateDpAsState(
            targetValue = if (animated) 0.dp else 20.dp,
            animationSpec = tween(durationMillis = 500),
            label = "feature_tile_offset_y_animation"
        )
        val alpha by animateFloatAsState(
            targetValue = if (animated) 1f else 0f,
            animationSpec = tween(durationMillis = 500),
            label = "feature_tile_alpha_animation"
        )

        // FeatureTile now wraps FeatureButton and applies animation to its cardModifier
        FeatureButton(
            icon = iconFor(feature),
            iconColor = primaryColor,
            label = labelFor(feature),
            cardModifier = Modifier.padding(8.dp).offset(y = offsetY).alpha(alpha),
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
    private fun iconFor(feature: ModulesResponse): ImageVector = when (feature.module_id) {
        3 -> Resources.Icon.document_share
        4 -> Resources.Icon.measurements
        5 -> Resources.Icon.chat
        7 -> Resources.Icon.meds
        8 -> Resources.Icon.activities
        20 -> Resources.Icon.memory
        19 -> Resources.Icon.hitber
        17 -> Resources.Icon.clock
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
        else -> {
            println("No label for feature: $feature")
            feature.module_name
        }
    }
}