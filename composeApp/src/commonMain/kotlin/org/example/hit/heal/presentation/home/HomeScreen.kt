package org.example.hit.heal.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.screens.MemoryScreen.MemoryScreen
import core.data.model.ModulesResponse
import kotlinx.coroutines.launch
import org.example.hit.heal.cdt.presentation.CDTLandingScreen
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.MEDIUM
import org.example.hit.heal.core.presentation.Green
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.logout
import org.example.hit.heal.core.presentation.Resources.String.logoutConfirmation
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.TextPrimary
import org.example.hit.heal.core.presentation.TextWhite
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.dialogs.BaseYesNoDialog
import org.example.hit.heal.core.presentation.components.cards.SimpleIconCard
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.entry.HitBerEntryScreen
import com.generic.marketTest.presentation.entry.MarketTestEntryScreen
import org.example.hit.heal.oriantation.feature.presentation.OriantationWelcomeScreen
import org.example.hit.heal.presentation.activities.ActivitiesScreen
import org.example.hit.heal.presentation.evaluation.AllEvaluationsScreen
import org.example.hit.heal.presentation.login.LoginScreen
import org.example.hit.heal.presentation.medication.presentaion.screens.mainMedication.MainMedicationScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.entryScreen.PassEntryScreen

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinViewModel()
        val features by viewModel.features.collectAsState(initial = emptyList())
        val navigator = LocalNavigator.currentOrThrow
        var showDialog by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val isLoading by viewModel.isLoading.collectAsState()

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
                val isPhone = maxWidth < 600.dp
                val columns = if (isPhone) 2 else 4

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
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = paddingSm)
                        )
                        Spacer(Modifier.height(spacingSm))
                        Text(
                            stringResource(Resources.String.take_pills),
                            fontSize = MEDIUM,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = paddingSm)
                        )
                    }

                    if (isLoading) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(Resources.String.loading),
                                fontSize = FontSize.EXTRA_LARGE,
                                color = primaryColor
                            )
                            Spacer(modifier = Modifier.width(spacingMd))
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                color = primaryColor,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columns), // Use the dynamic columns
                            contentPadding = PaddingValues(vertical = paddingMd),
                            horizontalArrangement = Arrangement.spacedBy(paddingMd),
                            verticalArrangement = Arrangement.spacedBy(paddingMd),
                            modifier = Modifier.padding(horizontal = paddingLg)
                        ) {
                            items(features.filter { it.active }) { feature ->
                                AnimatedFeatureTile(
                                    feature = feature,
                                    onClick = {
                                        navigateTo(feature.module_name, navigator)
                                    }
                                )
                            }
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
            elevation = CardDefaults.cardElevation(elevationMd),
            modifier = modifier,
            colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
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
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = EXTRA_MEDIUM,
                        color = TextWhite,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingSm),
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(Modifier.height(spacingMd))
                Column(
                    modifier = Modifier.padding(horizontal = paddingMd, vertical = paddingSm)
                ) {
                    content()
                }
            }
        }
    }

    @Composable
    fun AnimatedFeatureTile(
        feature: ModulesResponse,
        onClick: () -> Unit
    ) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            SimpleIconCard(
                title = feature.module_name,
                icon = {
                    Icon(
                        painter = painterResource(iconFor(feature.module_name)),
                        contentDescription = feature.module_name,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = paddingSm),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onClick = onClick
            )
        }
    }


    private fun navigateTo(moduleName: String, navigator: Navigator) {
        when (moduleName.lowercase()) {
            "cdt" -> navigator.push(CDTLandingScreen())
            "measurements" -> navigator.push(AllEvaluationsScreen())
            "activities" -> navigator.push(ActivitiesScreen())
            "hitber" -> navigator.push(HitBerEntryScreen())
            "orientation" -> navigator.push(OriantationWelcomeScreen())
            "memory" -> navigator.push(MemoryScreen())
            "medications" -> navigator.push(MainMedicationScreen())
            "pass" -> navigator.push(PassEntryScreen())
            "market" -> navigator.push(MarketTestEntryScreen())
            else -> {  }
        }
    }

    @Composable
    private fun iconFor(moduleName: String) = when (moduleName.lowercase()) {
        "document share" -> Resources.Icon.fileUploadIcon
        "measurements" -> Resources.Icon.evaluationLogo
        "chat" -> Resources.Icon.chatIcon
        "medications" -> Resources.Icon.medIcon
        "activities" -> Resources.Icon.exerciseIcon
        "memory" -> Resources.Icon.memoryModuleIcon
        "hitber" -> Resources.Icon.hitbearModuleIcon
        "cdt" -> Resources.Icon.clockIcon
        "orientation" -> Resources.Icon.memoryModuleIcon
        "pass" -> Resources.Icon.hitbearModuleIcon
        "market" -> Resources.Icon.marketIcon
        else -> Resources.Icon.binIcon
    }

    @Composable
    private fun labelFor(moduleName: String) = when (moduleName.lowercase()) {
        "document share" -> stringResource(Resources.String.document_share)
        "measurements" -> stringResource(Resources.String.measurements)
        "chat" -> stringResource(Resources.String.chat)
        "medications" -> stringResource(Resources.String.medications)
        "activities" -> stringResource(Resources.String.activities)
        "memory" -> stringResource(Resources.String.memory)
        "hitber" -> stringResource(Resources.String.hitber)
        "cdt" -> stringResource(Resources.String.clockTest)
        "orientation" -> "Orientation"
        "pass" -> stringResource(Resources.String.pass)
        "market" -> "Market"
        else -> moduleName
    }
}