package presentation.detailedContact

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.black_messages
import dmt_proms.pass.generated.resources.black_phone
import dmt_proms.pass.generated.resources.black_video
import dmt_proms.pass.generated.resources.whatsapp
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import presentation.components.ContactData
import presentation.components.circleWithPicture
import presentation.detailedContact.components.getContactChatData

class DetailedContactScreen(private val contact: ContactData) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val contactChatData = getContactChatData()

        BaseTabletScreen(
            title = "איש קשר",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .border(
                                width = 6.dp,
                                color = primaryColor,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = contact.name.first().toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                    }
                    Text(
                        text = contact.name,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        contactChatData.forEach { item ->
                            circleWithPicture(item)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    ContactDetailsSection(contact)
                }
            }
        )
    }

   @Composable
    fun ContactDetailsSection(contact: ContactData) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "פרטים ליצירת קשר",
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
                            modifier = Modifier.size(35.dp),
                            contentDescription = "טלפון"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = contact.phoneNumber,
                            fontSize = 16.sp,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(Res.drawable.black_messages),
                            modifier = Modifier.size(35.dp),
                            contentDescription = "הודעות"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(Res.drawable.black_video),
                            modifier = Modifier.size(35.dp),
                            contentDescription = "וידאו"
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "טלפון", fontSize = 16.sp,
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
                            text = "Whatsapp",
                            fontSize = 16.sp,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(Res.drawable.whatsapp),
                            modifier = Modifier.size(40.dp),
                            contentDescription = "WhatsApp"
                        )
                    }
                }
            }
        }
    }}