import org.example.hit.heal.presentation.medication.presentaion.screens.medicationListScreen.MedicationListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.medication_alarm
import dmt_proms.composeapp.generated.resources.pills
import org.example.hit.heal.core.presentation.FontSize.MEDIUM
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.heightLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.cards.SimpleRowCard
import org.example.hit.heal.presentation.medication.presentaion.screens.MedicationViewModel.MedicationViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main screen for Medication feature
 * Allows to navigate to Report Medication or Set Medication Alarm
 */

class MainMedicationScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<MedicationViewModel>()

        BaseScreen(
            title = stringResource(Resources.String.medications),
            config = ScreenConfig.PhoneConfig,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(55.dp))

                SimpleRowCard(
                    onClick = {
                        viewModel.setReport(true)
                        navigator.push(MedicationListScreen(isReport = true))
                    },
                    leading = painterResource(Res.drawable.pills),
                    title = stringResource(Resources.String.reportMedication),
                    containerColor = Color.White,
                    shape = RoundedCornerShape(20),
                    textStyle = TextStyle(fontSize = MEDIUM),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(paddingMd)
                        .height(heightLg)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SimpleRowCard(
                    onClick = {
                        viewModel.setReport(false)
                        navigator.push(MedicationListScreen(isReport = false))
                    },
                    leading = painterResource(Res.drawable.medication_alarm),
                    title = stringResource(Resources.String.setMedicationAlarm),
                    containerColor = Color.White,
                    shape = RoundedCornerShape(20),
                    textStyle = TextStyle(fontSize = MEDIUM),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(paddingMd)
                        .height(heightLg)
                )
            }
        }
    }
}

