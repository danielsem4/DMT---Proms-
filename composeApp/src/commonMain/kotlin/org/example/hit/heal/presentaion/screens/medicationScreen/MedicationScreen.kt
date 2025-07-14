import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.Medications.Medication

import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.presentaion.components.ReportMedicationDialog

import org.example.hit.heal.presentaion.components.SearchBar


import org.example.hit.heal.presentaion.screens.medicationReport.MedicationAlarmDetailsScreenContent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI




class MedicationScreen (private val isReport: Boolean) : Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<MedicationViewModel>()


        var selectedMedication by remember { mutableStateOf<Medication?>(null) }
        var showDialog by remember { mutableStateOf(false) }


        val keyboardController = LocalSoftwareKeyboardController.current
        var searchQuery by remember { mutableStateOf("") }
        val isLoading by viewModel.isLoading

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
            onPrevClick = { navigator.pop() },
            prevButtonText = stringResource(Resources.String.back),
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
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredMedications) { medication ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.chooseMedication(medication)
                                    if (isReport) {
                                        selectedMedication = medication
                                        showDialog = true
                                        viewModel.setMedicationName(medication.name)

                                    } else {

                                        navigator.push(
                                            MedicationAlarmDetailsScreenContent()
                                        )
                                    }
                                }
                                .clip(RoundedCornerShape(10.dp)),
                            backgroundColor = Color.White,
                            elevation = 0.dp

                        ) {
                            Text(
                                text = medication.name,
                                modifier = Modifier.padding(16.dp),

                                )
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


