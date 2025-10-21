// the cart screen
package com.example.finalprojectnew.stage2.presentation.cart

import com.example.finalprojectnew.stage2.data.catalog.ImageKeyMapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.stage2.presentation.Stage2Colors
import com.example.finalprojectnew.stage2.presentation.components.ListPillButton
import com.example.finalprojectnew.stage2.presentation.components.PrimaryMenuButton
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.DrawableResource
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.stage2_donation
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.asPaddingValues
import com.example.finalprojectnew.stage2.domain.model.donationItemIds
import androidx.compose.material3.HorizontalDivider

// Screen display
private const val COL_PRODUCT = 0.60f
private const val COL_QTY = 0.28f
private const val COL_REMOVE = 0.08f

@Composable
fun CartScreen(
    vm: CartViewModel,
    onViewList: () -> Unit,
    onViewDonationList: () -> Unit,
    onCheckout: () -> Unit,
    topButtonIcon: DrawableResource,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by vm.state.collectAsState()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) { // direction - right to left
        Box(
            modifier
                .fillMaxSize()
                .background(Stage2Colors.ScreenBg)
                .padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {

            Column(
                Modifier
                    .matchParentSize()
                    .padding(horizontal = 28.dp, vertical = 16.dp)
                    .padding(bottom = 96.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PrimaryMenuButton(
                        text = "חזרה לדף הראשי",
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(0.25f)
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        ListPillButton(
                            text = "לצפייה ברשימה",
                            iconPainter = painterResource(topButtonIcon),
                            onClick = onViewList
                        )
                        Spacer(Modifier.height(10.dp))
                        ListPillButton(
                            text = "לצפייה ברשימה לתרומה",
                            iconPainter = painterResource(Res.drawable.stage2_donation),
                            onClick = onViewDonationList,
                            edgeColor = Stage2Colors.FrameBlue
                        )
                    }
                }

                Text(
                    text = "סל הקניות",
                    color = Stage2Colors.BrandGreen,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "להסרה",
                        modifier = Modifier.weight(COL_REMOVE),
                        fontSize = 30.sp,
                        color = Stage2Colors.BodyText,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        "כמות",
                        modifier = Modifier.weight(COL_QTY),
                        fontSize = 30.sp,
                        color = Stage2Colors.BodyText,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        "מוצר",
                        modifier = Modifier.weight(COL_PRODUCT),
                        fontSize = 30.sp,
                        color = Stage2Colors.BodyText,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Stage2Colors.FrameGreen.copy(alpha = 0.25f)
                )

                if (state.isEmpty) { // if empty cart
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("הסל ריק", color = Stage2Colors.FrameGreen, fontSize = 50.sp)
                    }
                } else { // the cart is not empty
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(state.items, key = { it.productId }) { row ->
                            CartRow(
                                row = row,
                                onRemove = { vm.remove(row.productId) }, // remove is from the VM
                                onInc = { vm.inc(row.productId, row.quantity) }, // increase item "
                                onDec = { vm.dec(row.productId, row.quantity) } // less item "
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Stage2Colors.FrameGreen.copy(alpha = 0.15f)
                            )
                        }
                    }
                }
            }

            OutlinedButton(
                onClick = {
                    vm.saveFinalCart() // Ask the ViewModel to save a unified JSON.
                    onCheckout()
                },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 28.dp, bottom = 16.dp)
                    .height(72.dp)
                    .width(300.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Stage2Colors.Mint,
                    contentColor = Stage2Colors.FrameGreen
                ),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Text(
                    "לסיום\nלחצו כאן",
                    fontSize = 22.sp,
                    lineHeight = 26.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CartRow(
    row: CartRowUi,
    onRemove: () -> Unit,
    onInc: () -> Unit,
    onDec: () -> Unit
) {
    val isDonation =
        remember(row.productId) { donationItemIds.contains(row.productId) } // checking if an item is donation
    val rowShape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .clip(rowShape)
            .then(
                if (isDonation) { // if the item is donation - color blue
                    Modifier
                        .background(Stage2Colors.DonationTint)
                        .border(2.dp, Stage2Colors.FrameBlue, rowShape)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                } else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box( // remove column
            modifier = Modifier
                .weight(COL_REMOVE)
                .widthIn(min = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "הסר",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

        Box( // quantity column
            modifier = Modifier.weight(COL_QTY),
            contentAlignment = Alignment.Center
        ) {
            QuantityStepper(
                quantity = row.quantity,
                onInc = onInc,
                onDec = onDec
            )
        }

        Row( // item name + image - column
            modifier = Modifier.weight(COL_PRODUCT),
            verticalAlignment = Alignment.CenterVertically
        ) {
            getDrawableForImageKey(row.iconKey)?.let { resId ->
                Image(
                    painter = painterResource(resId),
                    contentDescription = row.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(86.dp)
                        .padding(end = 12.dp)
                )
            }
            Text(
                row.title,
                fontSize = 28.sp,
                color = if (isDonation) Stage2Colors.FrameBlue else Stage2Colors.BodyText,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun QuantityStepper( // checking the quantity
    quantity: Int,
    onInc: () -> Unit,
    onDec: () -> Unit,
    min: Int = 1
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircleStepperButton(onClick = onInc) {
            Icon(Icons.Filled.Add, contentDescription = "הוסף")
        }
        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 12.dp),
            fontSize = 22.sp
        )
        CircleStepperButton(
            onClick = onDec,
            enabled = quantity > min
        ) { // The − button is disabled if quantity>min
            Text("−", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun CircleStepperButton(
    onClick: () -> Unit,
    enabled: Boolean = true, // If enabled=false – the button is visible and blocked.
    content: @Composable () -> Unit
) {
    FilledTonalIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        colors = IconButtonDefaults.filledTonalIconButtonColors()
    ) { content() }
}

@Composable // Image mapping by key
fun getDrawableForImageKey(imageKey: String): DrawableResource? {
    return ImageKeyMapper.map(imageKey)
}
