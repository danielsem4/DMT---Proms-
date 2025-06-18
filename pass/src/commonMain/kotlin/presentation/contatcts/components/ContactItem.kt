package presentation.contatcts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors.primaryColor
import presentation.components.ContactData
import presentation.contatcts.ContactsViewModel

@Composable
fun ContactItem(contact: ContactData, viewModel: ContactsViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(120.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp)).clickable {
                viewModel.onContactClicked(contact)
            }
    ) {
        Box(
            modifier = Modifier.padding(20.dp)
                .size(80.dp)
                .background(color = primaryColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.name.first().toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = contact.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
