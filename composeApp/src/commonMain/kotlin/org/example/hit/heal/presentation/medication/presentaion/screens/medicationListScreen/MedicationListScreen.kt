import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.SearchBar
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


        val keyboardController = LocalSoftwareKeyboardController.current
        var searchQuery by remember { mutableStateOf("") }


        val userInf = remember { mutableStateOf<Pair<Int, Int>?>(null) }
        val medications: List<Medication> = viewModel.medications.value

        LaunchedEffect(Unit) {
            viewModel.loadMedications()
        }



        val filteredMedications: List<Medication> = remember(searchQuery, medications) {
            medications.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }


       BaseScreen(
            title = stringResource(Resources.String.medications),
            config = ScreenConfig.PhoneConfig,
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                SearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                    onItemClicked = { keyboardController?.hide() },
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(25.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    items(filteredMedications) { medication ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    try {
                                        viewModel.chooseMedication(medication)
                                        if (isReport) {
                                            selectedMedication = medication
                                            showDialog = true
                                            viewModel.setMedicationName(medication.name)
                                        } else {
                                            navigator.push(MedicationAlarmScreen(medication))
                                        }
                                    } catch (e: Exception) {
                                        println("Crash on medication click: ${e.message}")
                                        e.printStackTrace()
                                    }
                                }

                                .clip(RoundedCornerShape(15.dp))
                                .height(70.dp),
                            backgroundColor = Color.White,
                            elevation = 0.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = medication.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }


                    }
                }
            }
        }

        if (showDialog && selectedMedication != null) {
            ReportMedicationDialog(
                medicationName = selectedMedication!!.name,
                onDismiss = { showDialog = false },
                medication = selectedMedication!!
            )
        }
    }
}


