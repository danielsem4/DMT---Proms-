import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.Medications.Medication
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.pills
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes
import org.example.hit.heal.core.presentation.TextPrimary
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.SearchBar
import org.example.hit.heal.core.presentation.components.dialogs.ReportDialog
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.presentation.medication.presentaion.screens.MedicationViewModel.MedicationViewModel
import org.example.hit.heal.presentation.medication.presentaion.screens.medicationAlarm.MedicationAlarmScreen
import org.example.hit.heal.presentation.medication.presentaion.screens.medicationListScreen.components.ReportMedicationDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI




class MedicationListScreen (private val isReport: Boolean) : Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<MedicationViewModel>()

        var selectedMedication by remember { mutableStateOf<Medication?>(null) }
        var showDialog by remember { mutableStateOf(false) }

        val keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
        var searchQuery by remember { mutableStateOf("") }

        val medications: List<Medication> = viewModel.medications.value

        LaunchedEffect(Unit) { viewModel.loadMedications() }

        val filteredMedications: List<Medication> = remember(searchQuery, medications) {
            medications.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        BaseScreen(
            title = stringResource(Resources.String.medications),
            config = ScreenConfig.PhoneConfig,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Sizes.paddingMd)
            ) {
                Spacer(Modifier.height(Sizes.spacingMd))

                SearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                    onItemClicked = { keyboardController?.hide() },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(Sizes.spacingLg))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Sizes.paddingSm, bottom = Sizes.paddingMd),
                    verticalArrangement = Arrangement.spacedBy(Sizes.spacingXxl),
                    contentPadding = PaddingValues(bottom = Sizes.paddingXl)
                ) {
                    items(filteredMedications) { medication ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = Sizes.elevationMd,
                                    shape = RoundedCornerShape(Sizes.radiusXl),
                                    clip = false
                                )
                                .clickable {
                                    viewModel.chooseMedication(medication)
                                    if (isReport) {
                                        selectedMedication = medication
                                        showDialog = true
                                        viewModel.setMedicationName(medication.name)
                                    } else {
                                        navigator.push(MedicationAlarmScreen(medication))
                                    }
                                }
                                .clip(RoundedCornerShape(Sizes.radiusXl))
                                .height(Sizes.heightLg),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(Sizes.elevationLg)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = Sizes.paddingMd),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = medication.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = FontSize.EXTRA_MEDIUM,
                                    color = TextPrimary,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(Sizes.spacingLg))
            }
        }

        if (showDialog && selectedMedication != null) {
            ReportDialog(
                name = selectedMedication!!.name,
                selectedDate = viewModel.selectedDate,
                selectedTime = viewModel.selectedTime,
                isLoading = viewModel.isLoading.value,
                errorMessage = viewModel.errorMessage,
                isSuccess = viewModel.successMessageReport,
                onDismiss = { showDialog = false },
                onSave = {
                    // your existing save flow:
                    viewModel.validateAndSave(selectedMedication!!, selectedMedication!!.id)
                },
                icon = Res.drawable.pills,
                onDateUpdate = { date -> viewModel.updateDate(date) },
                onTimeUpdate = { time -> viewModel.updateTime(time) },
                onResetSuccess = { viewModel.resetSaveSuccess() }
            )
        }
    }
}


