package presentation.contatcts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import presentation.contatcts.components.allContacts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import presentation.components.ContactData

class ContactsViewModel : ViewModel()  {


    private val _contacts = MutableStateFlow(allContacts)
    val contacts: StateFlow<List<ContactData>> = _contacts

    var searchQuery = mutableStateOf("")
        private set

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        _contacts.value = if (query.isBlank()) {
            allContacts
        } else {
            allContacts.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}