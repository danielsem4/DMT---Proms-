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
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.Whatsapp
import dmt_proms.pass.generated.resources.black_messages
import dmt_proms.pass.generated.resources.black_phone
import dmt_proms.pass.generated.resources.black_video
import dmt_proms.pass.generated.resources.contact_details
import dmt_proms.pass.generated.resources.messages
import dmt_proms.pass.generated.resources.phone
import dmt_proms.pass.generated.resources.video
import dmt_proms.pass.generated.resources.whatsapp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.components.ContactData
import presentation.detailedContact.DetailedContactViewModel

@Composable
fun ContactDetailsSection(contact: ContactData, viewModel: DetailedContactViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = stringResource(Res.string.contact_details),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(Res.drawable.black_phone),
                        modifier = Modifier.size(35.dp)
                            .clickable { viewModel.onUserClicked(Res.string.phone) },
                        contentDescription = stringResource(Res.string.phone)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = contact.phoneNumber,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { viewModel.onUserClicked(Res.string.phone) }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(Res.drawable.black_messages),
                        modifier = Modifier.size(35.dp).clickable {
                            viewModel.onUserClicked(Res.string.messages)
                        },
                        contentDescription = stringResource(Res.string.messages)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(Res.drawable.black_video),
                        modifier = Modifier.size(35.dp)
                            .clickable { viewModel.onUserClicked(Res.string.video) },
                        contentDescription = stringResource(Res.string.video)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.phone), fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(Res.string.Whatsapp),
                        fontSize = 16.sp,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(Res.drawable.whatsapp),
                        modifier = Modifier.size(40.dp)
                            .clickable { viewModel.onUserClicked(Res.string.Whatsapp) },
                        contentDescription = stringResource(Res.string.Whatsapp)
                    )
                }
            }
        }
    }
}
