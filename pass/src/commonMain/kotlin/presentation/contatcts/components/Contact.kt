//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.input.key.Key.Companion.R
//import dmt_proms.pass.generated.resources.Res
//import dmt_proms.pass.generated.resources.person_names
//import dmt_proms.pass.generated.resources.phone_number
//import org.jetbrains.compose.resources.getStringArray
//import org.jetbrains.compose.resources.stringResource
//import presentation.components.ContactData
//
//@Composable
//fun ContactList() {
//    // זיכרון של רשימת אנשי קשר
//    val contactsList = remember { mutableStateOf<List<ContactData>>(emptyList()) }
//
//    LaunchedEffect(Unit) {
//        // קבלת השמות מתוך array ב-Compose Multiplatform
//        val names = getStringArray(Res.array.person_names).toList()
//
//        // יצירת הרשימה של אנשי הקשר
//        val contacts = names.map { name ->
//            ContactData(name, stringResource(Res.string.phone_number))
//        }
//
//        // עדכון הרשימה בסטייט
//        contactsList.value = contacts
//    }
//
//    // הצגת אנשי הקשר (נניח ברשימה)
//    // תוכל להוסיף כאן רכיב שמציג את אנשי הקשר
//}
