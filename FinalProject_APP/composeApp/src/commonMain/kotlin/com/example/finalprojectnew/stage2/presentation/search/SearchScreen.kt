@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)

package com.example.finalprojectnew.stage2.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.stage2.data.catalog.CatalogItem
import com.example.finalprojectnew.stage2.presentation.Stage2Colors
import com.example.finalprojectnew.stage2.presentation.components.PrimaryMenuButton
import com.example.finalprojectnew.stage2.presentation.components.ListPillButton
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.ic_list
import finalprojectnew.composeapp.generated.resources.stage2_cart
import finalprojectnew.composeapp.generated.resources.stage2_donation
import finalprojectnew.composeapp.generated.resources.stage2_magnifying_glass
import org.jetbrains.compose.resources.painterResource as painterResourceRes

// עגלה + מיפוי לדומיין/Ui
import com.example.finalprojectnew.di.Stage2Locator
import com.example.finalprojectnew.stage2.data.catalog.toDomain
import com.example.finalprojectnew.stage2.presentation.category.generic.ProductUi
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch

// זיהוי פריטי תרומה לפי IDs יציבים
import com.example.finalprojectnew.stage2.domain.model.donationItemIds

private const val ACTION_BTN_WIDTH_FRACTION = 0.26f
private val ACTION_BTN_HEIGHT = 84.dp
private val ACTION_BTN_FONT = 22.sp
private val ACTION_BTN_ICON = 56.dp

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    onOpenDonation: () -> Unit,
    onOpenShoppingList: () -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        val vm = remember { SearchViewModel() }
        val scope = rememberCoroutineScope()

        DisposableEffect(Unit) { onDispose { vm.clear() } }

        Scaffold(
            containerColor = Stage2Colors.ScreenBg,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Stage2Colors.ScreenBg
                        ),
                        title = {
                            Text(
                                text = "חיפוש לפי מוצר",
                                fontSize = 66.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Stage2Colors.BrandGreen,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                    PrimaryMenuButton(
                        text = "חזרה לדף הראשי",
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 12.dp, top = 12.dp)
                            .fillMaxWidth(0.25f)
                    )
                }
            },
            bottomBar = {
                Surface(color = Stage2Colors.ScreenBg) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // שמאל: כפתור סל קניות
                        CartPillButtonMint(
                            textLine1 = "לחצו לצפייה",
                            textLine2 = "בסל הקניות",
                            onClick   = onOpenCart,
                            icon      = painterResourceRes(Res.drawable.stage2_cart),
                            green     = Stage2Colors.FrameGreen,
                            modifier  = Modifier
                                .weight(ACTION_BTN_WIDTH_FRACTION)
                                .height(ACTION_BTN_HEIGHT)
                        )

                        Spacer(Modifier.width(12.dp))

                        // ימין: שני כפתורי רשימות
                        Column(
                            modifier = Modifier
                                .weight(ACTION_BTN_WIDTH_FRACTION),
                            horizontalAlignment = Alignment.End
                        ) {
                            ListPillButton(
                                text = "לצפייה ברשימה",
                                iconPainter = painterResourceRes(Res.drawable.ic_list),
                                onClick = onOpenShoppingList,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(10.dp))
                            ListPillButton(
                                text = "לצפייה ברשימה לתרומה",
                                iconPainter = painterResourceRes(Res.drawable.stage2_donation),
                                onClick = onOpenDonation,
                                modifier = Modifier.fillMaxWidth(),
                                edgeColor = Stage2Colors.FrameBlue
                            )
                        }
                    }
                }
            }
        ) { inner ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = vm.query,
                    onValueChange = vm::onQueryChange,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 30.sp,
                        textDirection = TextDirection.ContentOrRtl
                    ),
                    placeholder = {
                        Text(
                            text = "נא לרשום את מילת החיפוש. לדוגמה: גבינה/מלפפון/…",
                            style = LocalTextStyle.current.copy(
                                fontSize = 30.sp,
                                textDirection = TextDirection.ContentOrRtl
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    trailingIcon = {
                        Icon(
                            painter = painterResourceRes(Res.drawable.stage2_magnifying_glass),
                            contentDescription = "חיפוש"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.80f)
                        .height(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Stage2Colors.BrandGreen,
                        unfocusedBorderColor = Stage2Colors.BrandGreen.copy(alpha = 0.4f),
                        cursorColor = Stage2Colors.BrandGreen
                    )
                )

                Spacer(Modifier.height(12.dp))

                val results = vm.results
                val columns = if (results.size <= 1) 1 else 3

                // כמות קיימת מהעגלה
                val cartItems by Stage2Locator.cart.observe.collectAsState(initial = emptyList())
                val qtyById = remember(cartItems) { cartItems.associate { it.product.id to it.quantity } }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                    if (results.isEmpty() && vm.query.isNotBlank() && !vm.isLoading) {
                        item(span = { GridItemSpan(columns) }) {
                            Text(
                                text = "לא נמצאו תוצאות עבור \"${vm.query}\"",
                                color = Stage2Colors.BodyText,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 32.dp)
                            )
                        }
                    }

                    items(
                        items = results,
                        key = { "${it.category.name}_${it.id}" }
                    ) { item: CatalogItem ->
                        // CatalogItem -> ProductUi -> Product (דומיין)
                        val uiItem = ProductUi(
                            id         = item.id,
                            title      = item.title,
                            subtitle   = item.subtitle,
                            iconRes    = item.iconRes,
                            outOfStock = item.outOfStock   // ← חשוב: שומר את מצב המלאי
                        )
                        val product = uiItem.toDomain(category = item.category)

                        val currentQty = qtyById[product.id] ?: 0

                        // זיהוי מוצר תרומה לפי ID יציב
                        val isDonationItem = donationItemIds.contains(item.id)

                        com.example.finalprojectnew.stage2.presentation.category.generic.ProductCard(
                            title            = item.title,
                            subtitle         = item.subtitle,
                            iconRes          = item.iconRes,
                            scale            = 1f,
                            outOfStock       = item.outOfStock,   // ← מציג "אזל מהמלאי" וחוסם הוספה
                            initialQty       = currentQty,
                            onQuantityChange = { qty ->
                                if (!item.outOfStock) {
                                    scope.launch {
                                        // qty==0 מוחק, אחרת מעדכן/מוסיף
                                        Stage2Locator.cart.update(product, qty)
                                    }
                                }
                            },
                            // רקע כחול שקוף לפריטי תרומה
                            bgColor          = if (isDonationItem)
                                Stage2Colors.DonationTint
                            else
                                Stage2Colors.White
                        )
                    }
                }
            }
        }
    }
}

/* ===== כפתורי המינט (ללא שינוי לוגי) ===== */
@Composable
private fun CartPillButtonMint(
    textLine1: String,
    textLine2: String,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.painter.Painter,
    green: Color,
    labelColor: Color = green,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEFF8F2),
            contentColor   = labelColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = textLine1,
                    fontSize = ACTION_BTN_FONT,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = (ACTION_BTN_FONT.value + 2).sp,
                    color = labelColor
                )
                Text(
                    text = textLine2,
                    fontSize = ACTION_BTN_FONT,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = (ACTION_BTN_FONT.value + 2).sp,
                    color = labelColor
                )
            }
            Spacer(Modifier.width(10.dp))
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(ACTION_BTN_ICON),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
private fun DonationPillButtonMint(
    textLine1: String,
    textLine2: String,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.painter.Painter,
    green: Color,
    labelColor: Color,
    modifier: Modifier = Modifier
) = CartPillButtonMint(
    textLine1 = textLine1,
    textLine2 = textLine2,
    onClick   = onClick,
    icon      = icon,
    green     = green,
    labelColor = labelColor,
    modifier  = modifier
)
