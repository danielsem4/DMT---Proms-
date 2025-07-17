package org.example.hit.heal.presentation.home

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.ModulesResponse
import kotlinx.coroutines.launch
import org.example.hit.heal.cdt.presentation.CDTLandingScreen
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.MEDIUM
import org.example.hit.heal.core.presentation.FontSize.REGULAR
import org.example.hit.heal.core.presentation.Green
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.logout
import org.example.hit.heal.core.presentation.Resources.String.logoutConfirmation
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.elevationSm
import org.example.hit.heal.core.presentation.Sizes.iconSizeLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.TextWhite
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.entry.HitBerEntryScreen
import org.example.hit.heal.presentation.activities.ActivitiesScreen
import org.example.hit.heal.presentation.evaluation.AllEvaluationsScreen
import org.example.hit.heal.presentation.login.LoginScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.entryScreen.PassEntryScreen

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

        val staticFeatures = listOf(
            ModulesResponse(module_name = "Pass", active = true)
        )

        val allFeatures = if (features.isNotEmpty()) {
            (features + staticFeatures).filter { it.active }
        } else {
            emptyList()
        }

        LaunchedEffect(Unit) {
            viewModel.loadFeatures()
        }

        BaseScreen(
            title = stringResource(Resources.String.home),
            navigationIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        painter = painterResource(Resources.Icon.logoutIcon),
                        contentDescription = stringResource(logout),
                        tint = White,
                        modifier = Modifier.size(24.dp)
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
                            .padding(paddingMd)
                    ) {
                        Text(
                            stringResource(Resources.String.dont_forget),
                            fontSize = MEDIUM,
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(Modifier.height(spacingSm))
                        Text(
                            stringResource(Resources.String.take_pills),
                            fontSize = MEDIUM,
                            color = MaterialTheme.colors.onSurface
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        contentPadding = PaddingValues(paddingMd),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(allFeatures) { feature ->
                            FeatureTile(
                                feature = feature,
                                fontSize = REGULAR,
                                onClick = {
                                    navigateTo(feature.module_name, navigator)
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
                title = stringResource(logout),
                icon = Resources.Icon.logoutIcon,
                message = stringResource(logoutConfirmation),
                confirmButtonText = stringResource(yes),
                confirmButtonColor = Green,
                onConfirm = {

                    showDialog = false
                    scope.launch {
                        viewModel.logout()
                        navigator.replace(LoginScreen())
                    }
                },
                dismissButtonText = stringResource(no),
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
            shape = RoundedCornerShape(radiusMd),
            elevation = elevationMd,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = modifier
        ) {
            Column {
                // Header chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(radiusMd))
                        .background(primaryColor)
                ) {
                    Text(
                        text = stringResource(Resources.String.messages),
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = EXTRA_MEDIUM,
                        color = TextWhite,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingSm),
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(Modifier.height(spacingMd))
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
            shape = RoundedCornerShape(radiusLg),
            elevation = elevationSm,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(paddingSm)
                .clickable { onClick() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingMd),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(iconFor(feature.module_name)),
                    contentDescription = null,
                    modifier = Modifier.size(iconSizeLg),
                    tint = primaryColor
                )
                Spacer(Modifier.height(spacingSm))
                Text(
                    text = labelFor(feature.module_name),
                    fontSize = fontSize,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }

    private fun navigateTo(moduleName: String, navigator: Navigator) {
        when (moduleName) {
            "cdt" -> navigator.push(CDTLandingScreen())
            "measurements" -> navigator.push(AllEvaluationsScreen())
            "Activities" -> navigator.push(ActivitiesScreen())
            "HitBer" -> navigator.push(HitBerEntryScreen())
            "Pass" -> navigator.push(PassEntryScreen())
            else -> {}
        }
    }


    @Composable
    private fun iconFor(moduleName: String) = when (moduleName) {
        "document share" -> Resources.Icon.fileUploadIcon
        "measurements" -> Resources.Icon.evaluationLogo
        "chat" -> Resources.Icon.chatIcon
        "medications" -> Resources.Icon.medIcon
        "Activities" -> Resources.Icon.exerciseIcon
        "Memory" -> Resources.Icon.memoryModuleIcon
        "HitBer" -> Resources.Icon.hitbearModuleIcon
        "cdt" -> Resources.Icon.clockIcon
        "Orientation" -> Resources.Icon.memoryModuleIcon
        "Pass" -> Resources.Icon.hitbearModuleIcon
        else -> Resources.Icon.binIcon
    }

    @Composable
    private fun labelFor(moduleName: String) = when (moduleName) {
        "document share" -> stringResource(Resources.String.document_share)
        "measurements" -> stringResource(Resources.String.measurements)
        "chat" -> stringResource(Resources.String.chat)
        "medications" -> stringResource(Resources.String.medications)
        "Activities" -> stringResource(Resources.String.activities)
        "Memory" -> stringResource(Resources.String.memory)
        "HitBer" -> stringResource(Resources.String.hitber)
        "cdt" -> stringResource(Resources.String.clockTest)
        "Orientation" -> "Orientation"
        "Pass" -> stringResource(Resources.String.pass)
        else -> "Unknown"
    }
}