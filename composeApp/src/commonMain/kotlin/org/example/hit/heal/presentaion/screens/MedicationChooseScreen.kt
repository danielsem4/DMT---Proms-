import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

import org.example.hit.heal.presentaion.screens.BaseScreen


data class MedicationChooseScreen (val medicationName: String) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var selectedDosage by remember { mutableStateOf("One pill") }
        var selectedFrequency by remember { mutableStateOf("Once") }
        var notes by remember { mutableStateOf("") }

        val dosageOptions = listOf(
            "One pill",
            "1.5 pills",
            "Two pills",
            "2.5 pills",
            "Three pills"
        )

        val frequencyOptions = listOf(
            "Once",
            "Twice",
            "Three times",
            "Four times"
        )

        BaseScreen(
            title = medicationName,
            onPrevClick ={navigator.push(MedicationScreen())} ,
            onNextClick ={navigator.push(MedicationScreen())},
            prevButtonText = "Back",
            nextButtonText = "Save"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Dosage Section
                Text(
                    text = "Choose the pill dosage",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                CustomDropdownMenu(
                    selectedOption = selectedDosage,
                    options = dosageOptions,
                    onOptionSelected = { selectedDosage = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // Frequency Section
                Text(
                    text = "How many times per day",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                CustomDropdownMenu(
                    selectedOption = selectedFrequency,
                    options = frequencyOptions,
                    onOptionSelected = { selectedFrequency = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("Write your note here") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun CustomDropdownMenu(
        selectedOption: String,
        options: List<String>,
        onOptionSelected: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    ) {
                        Text(text = option)
                    }
                }
            }
        }
    }
}