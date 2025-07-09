package presentation.detailedContact.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.Resources.Icon.blackMessages
import org.example.hit.heal.core.presentation.Resources.Icon.blackPhone
import org.example.hit.heal.core.presentation.Resources.Icon.blackVideo
import org.example.hit.heal.core.presentation.Resources.Icon.whatsappIcon
import org.example.hit.heal.core.presentation.Resources.String.contactDetails
import org.example.hit.heal.core.presentation.Resources.String.messages
import org.example.hit.heal.core.presentation.Resources.String.phone
import org.example.hit.heal.core.presentation.Resources.String.video
import org.example.hit.heal.core.presentation.Resources.String.whatsapp
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.components.ContactData
import presentation.detailedContact.DetailedContactViewModel

@Composable
fun ContactDetailsSection(contact: ContactData, viewModel: DetailedContactViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(radiusMd))
            .padding(paddingMd)
    ) {
        Column {
            Text(
                text = stringResource(contactDetails),
                fontSize = EXTRA_REGULAR,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(blackPhone),
                        modifier = Modifier.size(35.dp)
                            .clickable { viewModel.onUserClicked(phone) },
                        contentDescription = stringResource(phone)
                    )
                    Spacer(modifier = Modifier.width(paddingSm))
                    Text(
                        text = contact.phoneNumber,
                        fontSize = EXTRA_REGULAR,
                        modifier = Modifier.clickable { viewModel.onUserClicked(phone) }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(blackMessages),
                        modifier = Modifier.size(35.dp).clickable {
                            viewModel.onUserClicked(messages)
                        },
                        contentDescription = stringResource(messages)
                    )
                    Spacer(modifier = Modifier.width(paddingSm))
                    Image(
                        painter = painterResource(blackVideo),
                        modifier = Modifier.size(35.dp)
                            .clickable { viewModel.onUserClicked(video) },
                        contentDescription = stringResource(video)
                    )
                }
            }
            Spacer(modifier = Modifier.width(paddingSm))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(phone), fontSize = EXTRA_REGULAR,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(paddingMd))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(whatsapp),
                        fontSize = EXTRA_REGULAR,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(whatsappIcon),
                        modifier = Modifier.size(40.dp)
                            .clickable { viewModel.onUserClicked(whatsapp) },
                        contentDescription = stringResource(whatsapp)
                    )
                }
            }
        }
    }
}
