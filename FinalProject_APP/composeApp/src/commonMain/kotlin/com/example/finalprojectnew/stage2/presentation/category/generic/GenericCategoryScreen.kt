package com.example.finalprojectnew.stage2.presentation.category.generic

import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.finalprojectnew.stage2.presentation.Stage2Colors
import com.example.finalprojectnew.stage2.presentation.components.ListPillButton
import com.example.finalprojectnew.stage2.presentation.components.Stage2CategorySidebar
import com.example.finalprojectnew.stage2.presentation.components.CartPillButtonMint // ← added: use the reusable cart pill
import com.example.finalprojectnew.stage2.domain.model.CategoryKey
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.ic_list
import finalprojectnew.composeapp.generated.resources.stage2_cart
import finalprojectnew.composeapp.generated.resources.stage2_donation
import finalprojectnew.composeapp.generated.resources.stage2_bakery_cake
import org.jetbrains.compose.resources.painterResource
import com.example.finalprojectnew.di.Stage2Locator
import com.example.finalprojectnew.stage2.data.catalog.toDomain
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.runtime.collectAsState
import com.example.finalprojectnew.stage2.domain.model.donationItemIds

@Composable
fun GenericCategoryScreen(
    currentKey: CategoryKey, // which category to show
    vm: CategoryViewModel, // vm that gives the screen mode (title/products)
    onSelectCategory: (CategoryKey) -> Unit,
    onOpenCart: () -> Unit,
    onOpenSearch: () -> Unit = {},
    onOpenShoppingList: () -> Unit = {},
    onOpenDonation: () -> Unit = {}
) {

    //CompositionLocalProvider = order from right to left
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface( // screen background
            color = Stage2Colors.ScreenBg,
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {
            // every time the category changes - vm.select(...) is called to construct a new CategoryUiState
            LaunchedEffect(currentKey) { vm.select(currentKey) }
            val ui = vm.state.collectAsState().value ?: return@Surface

            val scope =
                rememberCoroutineScope() //Run asynchronous operations (such as updating a basket) from the UI.

            // POPUP - new cake ! (bakeryscreen)
            var showPastriesAd by remember { mutableStateOf(false) }
            LaunchedEffect(currentKey) {
                if (currentKey.id == "pastries" &&
                    Stage2Locator.pastriesAdShownCount < Stage2Locator.pastriesAdMaxTimes
                ) {
                    Stage2Locator.pastriesAdShownCount++
                    showPastriesAd = true
                } else {
                    showPastriesAd = false
                }
            }

            // // Reads the cart to display the existing quantity on each card
            val cartItems by Stage2Locator.cart.observe.collectAsState(initial = emptyList())
            val qtyById = remember(cartItems) {
                cartItems.associate { it.product.id to it.quantity }
            }
            // sizes-
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val w = maxWidth.value
                val scale = when {
                    w >= 1100f -> 1.30f
                    w >= 900f -> 1.18f
                    w >= 700f -> 1.08f
                    else -> 1.00f
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp * scale)
                ) {
                    // sidebar
                    Stage2CategorySidebar(
                        current = ui.key.id,
                        onSelect = { id -> CategoryKey.fromId(id)?.let(onSelectCategory) },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(220.dp * scale),
                        scale = scale
                    )

                    Spacer(Modifier.width(16.dp * scale))

                    //Title and description line + search button
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Spacer(Modifier.height(16.dp * scale))

                        Text(
                            text = ui.title,
                            color = Stage2Colors.BrandGreen,
                            fontSize = (56.sp * scale),
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(6.dp * scale))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "לבחירת מוצר, לחצו עליו ובחרו כמות רצויה",
                                color = Stage2Colors.BodyText,
                                fontSize = (22.sp * scale),
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(Modifier.width(8.dp * scale))
                            Button(
                                onClick = onOpenSearch,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Stage2Colors.Mint,
                                    contentColor = Stage2Colors.FrameGreen
                                ),
                                shape = RoundedCornerShape(12.dp * scale),
                                elevation = ButtonDefaults.buttonElevation(0.dp),
                                contentPadding = PaddingValues(
                                    horizontal = 16.dp * scale,
                                    vertical = 10.dp * scale
                                )
                            ) {
                                Text(
                                    "חיפוש",
                                    fontSize = (20.sp * scale),
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp * scale))

                        // Product Grid
                        val gridState = rememberLazyGridState()
                        //Keep scrolling+In each new category — scroll to the top
                        LaunchedEffect(currentKey) { gridState.scrollToItem(0) }
                        val columns = if (w >= 800f) 3 else 2

                        LazyVerticalGrid(
                            state = gridState,
                            columns = GridCells.Fixed(columns),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(12.dp * scale),
                            verticalArrangement = Arrangement.spacedBy(12.dp * scale),
                            contentPadding = PaddingValues(bottom = 12.dp * scale)
                        ) {
                            items(ui.items, key = { it.id }) { item ->
                                val product = item.toDomain(ui.key)
                                val currentQty = qtyById[product.id] ?: 0

                                // identify donation product
                                val isDonationItem = donationItemIds.contains(item.id)

                                ProductCard(
                                    title = item.title,
                                    subtitle = item.subtitle,
                                    iconRes = item.iconRes,
                                    scale = scale,
                                    outOfStock = item.outOfStock,
                                    initialQty = currentQty,
                                    onQuantityChange = { qty ->
                                        if (!item.outOfStock) {
                                            scope.launch {
                                                // // qty==0 delete, otherwise update/add
                                                Stage2Locator.cart.update(product, qty)
                                            }
                                        }
                                    },
                                    // blue background for donation item
                                    bgColor = if (isDonationItem)
                                        Stage2Colors.DonationTint
                                    else
                                        Stage2Colors.White
                                )
                            }
                        }

                        // At the bottom of the screen - cart button + cart/donation list buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp * scale),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // shopping cart button (component)
                            CartPillButtonMint(
                                textLine1 = "לחצו לצפייה\nבסל הקניות",
                                icon = painterResource(Res.drawable.stage2_cart),
                                onClick = onOpenCart,
                                modifier = Modifier
                                    .shadow(
                                        6.dp * scale,
                                        RoundedCornerShape(12.dp * scale),
                                        clip = false
                                    )
                            )

                            // cart/donation list button
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                ListPillButton(
                                    text = "לצפייה ברשימה",
                                    iconPainter = painterResource(Res.drawable.ic_list),
                                    onClick = onOpenShoppingList
                                )
                                Spacer(Modifier.width(10.dp))
                                ListPillButton(
                                    text = "לצפייה ברשימה לתרומה",
                                    iconPainter = painterResource(Res.drawable.stage2_donation),
                                    onClick = onOpenDonation,
                                    edgeColor = Stage2Colors.FrameBlue
                                )
                            }
                        }
                    }
                }
            }

            if (showPastriesAd) {
                PastriesAdDialog(onClose = { showPastriesAd = false })
            }
        }
    }
}

@Composable
fun StyledNoticeDialog(
    title: String,
    message: String? = null,
    image: Painter? = null,
    primaryText: String = "סגור",
    onPrimary: () -> Unit,
    onDismiss: () -> Unit = onPrimary
) {
    Dialog(onDismissRequest = onDismiss) {
        val frame = Stage2Colors.FrameGreen
        val radius = 20.dp

        Surface(
            color = Stage2Colors.White,
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(radius),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .border(3.dp, frame, RoundedCornerShape(radius))
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 18.dp,
                    start = 22.dp,
                    end = 22.dp,
                    bottom = 22.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = Stage2Colors.BrandGreen,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                if (image != null) {
                    Spacer(Modifier.height(10.dp))
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
                }
                if (!message.isNullOrBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = message,
                        color = frame,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = onPrimary,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Stage2Colors.Mint,
                        contentColor = frame
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(primaryText, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}

@Composable
fun PastriesAdDialog(onClose: () -> Unit) {
    StyledNoticeDialog(
        title = "חדש במחלקת המאפים!",
        message = "עוגת שמרים טרייה",
        image = painterResource(Res.drawable.stage2_bakery_cake),
        primaryText = "סגור",
        onPrimary = onClose
    )
}
