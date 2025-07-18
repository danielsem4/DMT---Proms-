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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Resources.Icon.deleteNumberIcon
import org.example.hit.heal.core.presentation.Resources.Icon.whitePhone
import org.example.hit.heal.core.presentation.Resources.String.deleteNumber
import org.example.hit.heal.core.presentation.Resources.String.dial
import org.example.hit.heal.core.presentation.Resources.String.key0
import org.example.hit.heal.core.presentation.Resources.String.key1
import org.example.hit.heal.core.presentation.Resources.String.key2
import org.example.hit.heal.core.presentation.Resources.String.key3
import org.example.hit.heal.core.presentation.Resources.String.key4
import org.example.hit.heal.core.presentation.Resources.String.key5
import org.example.hit.heal.core.presentation.Resources.String.key6
import org.example.hit.heal.core.presentation.Resources.String.key7
import org.example.hit.heal.core.presentation.Resources.String.key8
import org.example.hit.heal.core.presentation.Resources.String.key9
import org.example.hit.heal.core.presentation.Resources.String.keyHash
import org.example.hit.heal.core.presentation.Resources.String.keyStar
import org.example.hit.heal.core.presentation.Resources.String.phone
import org.example.hit.heal.core.presentation.Sizes.heightMd
import org.example.hit.heal.core.presentation.Sizes.iconSizeLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.Sizes.widthLg
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DialDialog(
    enteredNumber: String,
    onNumberClicked: (String) -> Unit,
    onDial: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(0.5f)
                    .background(Color(0xFFE8E8E8)).align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(paddingMd)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(heightMd)
                        .border(2.dp, Color.Black, RoundedCornerShape(radiusMd))
                        .padding(paddingSm)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(deleteNumberIcon),
                            contentDescription = stringResource(deleteNumber),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onDeleteClicked() }
                        )
                        Text(
                            text = enteredNumber,
                            fontSize = EXTRA_MEDIUM,
                            color = Color(0xFF333333),
                        )
                    }
                }


                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(paddingMd)
                ) {
                    val keypadKeys = listOf(
                        listOf(stringResource(key3), stringResource(key2), stringResource(key1)),
                        listOf(stringResource(key6), stringResource(key5), stringResource(key4)),
                        listOf(stringResource(key9), stringResource(key8), stringResource(key7)),
                        listOf(
                            stringResource(keyHash),
                            stringResource(key0),
                            stringResource(keyStar)
                        )
                    )

                    keypadKeys.forEach { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(paddingMd)
                        ) {
                            row.forEach { key ->
                                Button(
                                    onClick = { onNumberClicked(key) },
                                    modifier = Modifier.width(100.dp).height(70.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                                ) {
                                    Text(text = key, fontSize = EXTRA_LARGE, color = Color.Black)
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
                            .width(widthLg)
                            .padding(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(dial),
                                fontSize = EXTRA_MEDIUM,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(paddingSm))
                            Image(
                                painter = painterResource(whitePhone),
                                contentDescription = stringResource(phone),
                                modifier = Modifier.size(iconSizeLg)
                            )
                        }
                    }
                }
            }

        }
    }
}