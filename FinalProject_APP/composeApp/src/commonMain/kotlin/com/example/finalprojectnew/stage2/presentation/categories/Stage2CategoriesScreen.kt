// all the categories screen
package com.example.finalprojectnew.stage2.presentation.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.ic_list
import finalprojectnew.composeapp.generated.resources.stage2_cart
import finalprojectnew.composeapp.generated.resources.stage2_category_cleaning
import finalprojectnew.composeapp.generated.resources.stage2_category_dry
import finalprojectnew.composeapp.generated.resources.stage2_category_frozen
import finalprojectnew.composeapp.generated.resources.stage2_category_fruits
import finalprojectnew.composeapp.generated.resources.stage2_category_meat
import finalprojectnew.composeapp.generated.resources.stage2_category_milk
import finalprojectnew.composeapp.generated.resources.stage2_category_pastries
import finalprojectnew.composeapp.generated.resources.stage2_category_vegetables
import finalprojectnew.composeapp.generated.resources.stage2_donation
import finalprojectnew.composeapp.generated.resources.stage2_magnifying_glass
import com.example.finalprojectnew.stage2.presentation.components.ListPillButton
import com.example.finalprojectnew.stage2.presentation.components.CartPillButtonMint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import com.example.finalprojectnew.stage2.presentation.Stage2Colors

private val ScreenBg = Color(0xFFFEFAF1)
private val BrandGreen = Color(0xFF046030)
private val FrameGreen = Stage2Colors.FrameGreen
private val TileWhite = Stage2Colors.TileWhite

data class Category(
    val id: String,
    val title: String,
    val icon: Painter
)

@Composable
fun Stage2CategoriesScreen(
    onCategorySelected: (Category) -> Unit, // When a category is clicked, moves it to the next screen
    onOpenCart: () -> Unit,
    onOpenSearch: () -> Unit = {},
    onOpenDonation: () -> Unit = onOpenCart,
    onOpenShoppingList: () -> Unit = onOpenCart
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) { //right to left
        Surface( // the background of the screen
            color = ScreenBg,
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {
            BoxWithConstraints(Modifier.fillMaxSize()) { // Display settings of the screen
                val w = maxWidth.value
                val scale = when {
                    w >= 1100f -> 1.35f
                    w >= 900f -> 1.22f
                    w >= 700f -> 1.10f
                    else -> 1.00f
                }
                val columns = when {
                    w >= 900f -> 4
                    w >= 650f -> 3
                    else -> 2
                }

                Box(Modifier.fillMaxSize().padding(16.dp * scale)) {
                    Column(
                        modifier = Modifier.align(Alignment.TopStart),
                        horizontalAlignment = Alignment.Start
                    ) {
                        ListPillButton(
                            text = "לצפייה ברשימה",
                            iconPainter = painterResource(Res.drawable.ic_list),
                            onClick = onOpenShoppingList
                        )
                        Spacer(Modifier.height(10.dp))
                        ListPillButton(
                            text = "לצפייה ברשימה לתרומה",
                            iconPainter = painterResource(Res.drawable.stage2_donation),
                            onClick = onOpenDonation,
                            edgeColor = Stage2Colors.FrameBlue
                        )
                    }
                    SearchMenuButton(
                        text = "חיפוש",
                        icon = painterResource(Res.drawable.stage2_magnifying_glass),
                        onClick = onOpenSearch,
                        scale = scale,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }

                val categories = listOf( // the categories grid
                    Category(
                        "frozen",
                        "קפואים",
                        painterResource(Res.drawable.stage2_category_frozen)
                    ),
                    Category(
                        "fruits",
                        "פירות",
                        painterResource(Res.drawable.stage2_category_fruits)
                    ),
                    Category(
                        "vegetables",
                        "ירקות",
                        painterResource(Res.drawable.stage2_category_vegetables)
                    ),
                    Category(
                        "meat",
                        "מוצרי בשר",
                        painterResource(Res.drawable.stage2_category_meat)
                    ),
                    Category(
                        "milk",
                        "מוצרי חלב",
                        painterResource(Res.drawable.stage2_category_milk)
                    ),
                    Category(
                        "dry",
                        "יבשים ותבלינים",
                        painterResource(Res.drawable.stage2_category_dry)
                    ),
                    Category(
                        "pastries",
                        "מאפים",
                        painterResource(Res.drawable.stage2_category_pastries)
                    ),
                    Category(
                        "cleaning",
                        "ניקיון וחד''פ",
                        painterResource(Res.drawable.stage2_category_cleaning)
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp * scale),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(20.dp * scale))

                    Text(
                        text = "קטגוריות",
                        color = BrandGreen,
                        fontSize = (56.sp * scale),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(6.dp * scale))

                    Text(
                        text = "לחצו על הקטגוריה הרצויה",
                        color = Color(0xFF000000),
                        fontSize = (22.sp * scale),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(18.dp * scale))

                    LazyVerticalGrid( // shows all the categories
                        columns = GridCells.Fixed(columns),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(18.dp * scale),
                        verticalArrangement = Arrangement.spacedBy(18.dp * scale),
                        contentPadding = PaddingValues(bottom = 18.dp * scale)
                    ) {
                        items(categories, key = { it.id }) { cat ->
                            CategoryTile(
                                title = cat.title,
                                icon = cat.icon,
                                scale = scale,
                                onClick = { onCategorySelected(cat) }
                            )
                        }
                    }

                    // cart button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp * scale),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CartPillButtonMint(
                            onClick = onOpenCart,
                            icon = painterResource(Res.drawable.stage2_cart)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryTile( // one category card
    title: String,
    icon: Painter,
    scale: Float,
    onClick: () -> Unit
) {
    val radius = 14.dp * scale
    val stroke = 3.dp * scale

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp * scale)
            .shadow(6.dp * scale, RoundedCornerShape(radius), clip = false)
            .clip(RoundedCornerShape(radius))
            .background(Color.White)
            .border(stroke, SolidColor(FrameGreen), RoundedCornerShape(radius))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp * scale, horizontal = 10.dp * scale)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(radius - 2.dp * scale))
                .background(Color(0xFFF0FBF4).copy(alpha = 0.45f))
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = title,
                color = FrameGreen,
                fontSize = (26.sp * scale),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(55.dp * scale)
            )
        }
    }
}

@Composable
private fun SearchMenuButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    scale: Float,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp * scale)
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp * scale)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = TileWhite,
            contentColor = FrameGreen
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 2.dp * scale,
            brush = SolidColor(FrameGreen)
        ),
        shape = RoundedCornerShape(12.dp * scale),
        contentPadding = PaddingValues(horizontal = 14.dp * scale, vertical = 8.dp * scale)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp * scale)
            )
            Spacer(Modifier.width(10.dp * scale))
            Text(
                text = text,
                fontSize = (18.sp * scale),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
