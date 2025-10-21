@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)

package com.example.finalprojectnew.stage2.presentation.shoppinglist

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.stage2.domain.model.shoppingCategories
import com.example.finalprojectnew.stage2.domain.model.donationListItems
import com.example.finalprojectnew.stage2.presentation.Stage2Colors
import com.example.finalprojectnew.stage2.presentation.components.PrimaryMenuButton


@Composable // Creates a flat list- combines all categories into one list of texts.
fun ShoppingListScreen(
    onCloseList: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    // The regular list – is extracted from the domain (catalogized), and reduced to a flat list for display
    val allItems: List<String> = shoppingCategories.flatMap { it.items }
    GenericListScreen(
        title = "רשימת קניות",
        items = allItems,
        onCloseList = onCloseList
    )
}

@Composable
fun DonationListScreen(
    onCloseList: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    GenericListScreen(
        title = "רשימת תרומה",
        items = donationListItems,
        onCloseList = onCloseList
    )
}

@Composable
private fun GenericListScreen(
    title: String,
    items: List<String>,
    onCloseList: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            containerColor = Stage2Colors.ScreenBg,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Stage2Colors.ScreenBg
                    ),
                    title = {
                        Text(
                            text = title,
                            fontSize = 60.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Stage2Colors.BrandGreen,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            },
            bottomBar = {
                Surface(color = Stage2Colors.ScreenBg) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 28.dp)
                            .navigationBarsPadding()
                    ) {
                        PrimaryMenuButton(
                            text = "לסגירת הרשימה",
                            onClick = onCloseList,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth(0.4f)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                BigParchmentCard(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .fillMaxWidth(0.90f)
                        .widthIn(max = 800.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 18.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        userScrollEnabled = false
                    ) {
                        items(items) { line ->
                            ShoppingItemRow(line)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BigParchmentCard(
    modifier: Modifier = Modifier,
    corner: Int = 22,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(corner.dp)
    Surface(
        modifier = modifier.clip(shape),
        color = Stage2Colors.White,
        shape = shape,
        border = BorderStroke(1.5.dp, Color.Black),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(content = content)
    }
}

@Composable
private fun ShoppingItemRow(text: String) {
    Text(
        text = "• $text",
        fontSize = 28.sp,
        lineHeight = 26.sp,
        color = Stage2Colors.BodyText,
        modifier = Modifier.fillMaxWidth()
    )
}
