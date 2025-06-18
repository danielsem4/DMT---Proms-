package presentation.dialScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.delete_number
import dmt_proms.pass.generated.resources.dial
import dmt_proms.pass.generated.resources.phone
import dmt_proms.pass.generated.resources.white_phone
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DialDialog(
    enteredNumber: String,
    onNumberClicked: (String) -> Unit,
    onDial: () -> Unit,
    onDeleteClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(0.5f)
                .background(Color(0xFFD3D3D3)).align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth().height(60.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(Res.drawable.delete_number),
                        contentDescription = stringResource(Res.string.delete_number),
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onDeleteClicked() }
                    )
                    Text(
                        text = enteredNumber,
                        fontSize = 25.sp,
                        color = Color.Gray,
                    )
                }
            }


            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val numbers = listOf(
                    listOf("3", "2", "1"),
                    listOf("6", "5", "4"),
                    listOf("9", "8", "7"),
                    listOf("#", "0", "*")
                )
                numbers.forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach { number ->
                            Button(
                                onClick = { onNumberClicked(number) },
                                modifier = Modifier.width(100.dp).height(70.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                            ) {
                                Text(text = number, fontSize = 40.sp, color = Color.Black)
                            }
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDial,
                    modifier = Modifier
                        .align(Alignment.BottomCenter).height(100.dp)
                        .width(160.dp)
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.dial),
                            fontSize = 25.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(Res.drawable.white_phone),
                            contentDescription = stringResource(Res.string.phone),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

        }
    }
}