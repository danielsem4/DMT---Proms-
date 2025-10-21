package com.example.finalprojectnew.stage2.presentation.category.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.stage2.presentation.Stage2Colors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProductCard(
    title: String,
    subtitle: String = "",
    iconRes: DrawableResource,
    scale: Float = 1f,
    outOfStock: Boolean = false,
    initialQty: Int = 0,
    onQuantityChange: (Int) -> Unit,
    bgColor: Color = Stage2Colors.White
) {
    val radius = 16.dp * scale
    val frame = Stage2Colors.FrameGreen

    var qty by remember { mutableStateOf(0) }
    LaunchedEffect(initialQty) { qty = initialQty }

    val cardHeight = 260.dp * scale

    Surface( // card design
        shape = RoundedCornerShape(radius),
        color = bgColor,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .border(2.dp, frame, RoundedCornerShape(radius))
    ) {
        Box(Modifier.fillMaxSize()) { // option to display layer upon layer - for example, item that out of stock
            Column( //
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (outOfStock) 0.45f else 1f), // when outofstock -> 0.45f of clearence
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = title,
                        modifier = Modifier.size(160.dp * scale)
                    )
                }

                //title of product
                Spacer(Modifier.height(6.dp * scale))

                Text(
                    text = title,
                    color = Stage2Colors.BodyText,
                    fontSize = (20.sp * scale),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                //subtitle
                if (subtitle.isNotBlank()) {
                    Spacer(Modifier.height(2.dp * scale))
                    Text(
                        text = subtitle,
                        color = Stage2Colors.BodyText.copy(alpha = 0.7f),
                        fontSize = (15.sp * scale),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(4.dp * scale))

                //QuantityPicker — Internal composable that draws +/– and a number.
                //Clicking on the +/- both updates the local state (qty) and reports to the parent (onQuantityChange).

                QuantityPicker( //
                    qty = qty,
                    onInc = {
                        qty++
                        onQuantityChange(qty)
                    },
                    onDec = {
                        if (qty > 0) {
                            qty--
                            onQuantityChange(qty)
                        }
                    },
                    scale = scale,
                    enabled = !outOfStock //If outOfStock — enabled = false → prevents clicks.
                ).let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp * scale)
                    ) { it }
                }
            }

            if (outOfStock) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "אזל מהמלאי",
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Black,
                        fontSize = (20.sp * scale),
                        modifier = Modifier
                            .padding(top = 8.dp * scale)
                            .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp * scale, vertical = 4.dp * scale)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuantityPicker(
    qty: Int,
    onInc: () -> Unit,
    onDec: () -> Unit,
    scale: Float,
    enabled: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        FilledTonalIconButton(
            onClick = onInc,
            enabled = enabled,
            modifier = Modifier.size(36.dp * scale)
        ) { Text("+", fontSize = (18.sp * scale), fontWeight = FontWeight.Bold) }

        Text(
            qty.toString(),
            modifier = Modifier.padding(horizontal = 10.dp * scale),
            fontSize = (18.sp * scale),
            fontWeight = FontWeight.Bold,
            color = Stage2Colors.BodyText
        )

        FilledTonalIconButton(
            onClick = onDec,
            enabled = enabled && qty > 0,
            modifier = Modifier.size(36.dp * scale)
        ) { Text("−", fontSize = (18.sp * scale), fontWeight = FontWeight.Bold) }
    }
}
