import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import org.example.hit.heal.presentaion.screens.BaseScreen
import org.example.hit.heal.presentaion.components.SearchBar


class Medication(val name: String)

class MedicationScreen () : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val sampleMedications = listOf(
            Medication("Edvil"),
            Medication("Acamol"),
            Medication("FANHDI INJ 500IU"),
            Medication("Paracetamol"),
            Medication("Ibuprofen"),
            Medication("Aspirin"),
            Medication("Nurofen"),
            Medication("Vitamin C")
        )

        val keyboardController = LocalSoftwareKeyboardController.current
        var searchQuery by remember { mutableStateOf("") }
        val filteredMedications = remember(searchQuery) {
            sampleMedications.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
        BaseScreen(title = "Medications") {
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
                                .clickable { navigator.push(MedicationChooseScreen(medication.name)) }
                                .clip(RoundedCornerShape(10.dp)),
                            backgroundColor = Color.White,
                            elevation = 0.dp

                        ) {
                            Text(
                                text = medication.name,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}


