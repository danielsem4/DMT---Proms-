// This file builds all the small visual parts of the conveyor game:
// the conveyor, the checklist, and the product circles with images.

package com.example.finalprojectnew.presentation.stage1.conveyor_game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope

import org.jetbrains.compose.resources.painterResource
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.baguette
import finalprojectnew.composeapp.generated.resources.baking_powder
import finalprojectnew.composeapp.generated.resources.black_pepper
import finalprojectnew.composeapp.generated.resources.broccoli
import finalprojectnew.composeapp.generated.resources.bulgarian_cheese
import finalprojectnew.composeapp.generated.resources.cacao
import finalprojectnew.composeapp.generated.resources.chocolate
import finalprojectnew.composeapp.generated.resources.conveyor_new
import finalprojectnew.composeapp.generated.resources.cucumber
import finalprojectnew.composeapp.generated.resources.eggs
import finalprojectnew.composeapp.generated.resources.flour
import finalprojectnew.composeapp.generated.resources.kanola_oil
import finalprojectnew.composeapp.generated.resources.lemon
import finalprojectnew.composeapp.generated.resources.milk3precent
import finalprojectnew.composeapp.generated.resources.olive_oil
import finalprojectnew.composeapp.generated.resources.olives
import finalprojectnew.composeapp.generated.resources.salt
import finalprojectnew.composeapp.generated.resources.suger
import finalprojectnew.composeapp.generated.resources.tomato
import finalprojectnew.composeapp.generated.resources.vanil
import finalprojectnew.composeapp.generated.resources.white_cheese
import finalprojectnew.composeapp.generated.resources.yellow_cheese

data class ProductItem(val name: String, val isCorrect: Boolean)
enum class SelectionState { Unselected, Correct, Wrong }

// Conveyor background image
@Composable
internal fun ConveyorBackground(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.conveyor_new),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.background(Color.White)
    )
}

// Ingredients checklist (3 columns)
@Composable
internal fun IngredientsChecklist(
    items: List<String>,
    checked: SnapshotStateList<Boolean>
) {
    val col1 = items.take(3)
    val col2 = items.drop(3).take(3)
    val col3 = items.drop(6)

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            col1.forEachIndexed { i, t -> IngredientCheckRow(t, checked[i]) }
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            col2.forEachIndexed { i, t -> IngredientCheckRow(t, checked[i + 3]) }
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            col3.forEachIndexed { i, t -> IngredientCheckRow(t, checked[i + 6]) }
        }
    }
}

// a single row inside the checklist: label + checkbox.
@Composable
private fun IngredientCheckRow(text: String, checked: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End, // right-aligned for Hebrew
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            textAlign = TextAlign.Right,
            color = Color(0xFF1A1A1A)
        )
        Spacer(Modifier.width(8.dp))
        Checkbox(checked = checked, onCheckedChange = null) // read-only checkbox (foe UI only)
    }
}

// Product circle with image and optional ✓/✕ badge
@Composable
internal fun ProductChip(
    name: String,
    selection: SelectionState,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(Color.White, CircleShape) // circular white base
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            val painter = productPainter(name) // map name → resource painter
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.82f), // The image will be 82% of the circle
                    contentScale = ContentScale.Fit
                )
            } else {  // If there is no image - display a short text as a backup
                Text(
                    text = name.take(4),
                    fontSize = 22.sp,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // green V \ red X
            when (selection) {
                SelectionState.Correct    -> Badge("✓", Color(0xFF2E7D32))
                SelectionState.Wrong      -> Badge("✕", Color(0xFFC62828))
                SelectionState.Unselected -> {}
            }
        }
        Text(
            text = name,
            fontSize = 24.sp,
            color = Color.White,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(220.dp)
        )
    }
}

// V\X in the corner of the circle
@Composable
private fun BoxScope.Badge(symbol: String, color: Color) {
    Box(
        modifier = Modifier
            .size(75.dp)
            .align(Alignment.TopEnd)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, fontSize = 40.sp, color = color)
    }
}

// Map ingredient name -> ingredient picture
@Composable
private fun productPainter(name: String): Painter? {
    val key = name
        // clean and normalized the text
        .replace(" ", "")
        .replace("־", "")
        .replace(Regex("""\d+"""), "")  // מוחק כל ספרות מחוברות (כמו "3ביצים")


    return runCatching {
        when (key) {
            "מלפפון"                -> painterResource(Res.drawable.cucumber)
            "עגבנייה", "עגבניה"     -> painterResource(Res.drawable.tomato)
            "לימון"                  -> painterResource(Res.drawable.lemon)
            "זיתים"                  -> painterResource(Res.drawable.olives)
            "ברוקולי"                -> painterResource(Res.drawable.broccoli)
            "ביצים"                  -> painterResource(Res.drawable.eggs)
            "חלב"                    -> painterResource(Res.drawable.milk3precent)
            "גבינהבולגרית"           -> painterResource(Res.drawable.bulgarian_cheese)
            "גבינהלבנה"              -> painterResource(Res.drawable.white_cheese)
            "גבינהצהובה"             -> painterResource(Res.drawable.yellow_cheese)
            "קמח"                    -> painterResource(Res.drawable.flour)
            "סוכר"                   -> painterResource(Res.drawable.suger)
            "קקאו"                   -> painterResource(Res.drawable.cacao)
            "אבקתאפיה", "אבקתאפייה"  -> painterResource(Res.drawable.baking_powder)
            "שוקולד"                 -> painterResource(Res.drawable.chocolate)
            "באגט"                   -> painterResource(Res.drawable.baguette)
            "מלח"                    -> painterResource(Res.drawable.salt)
            "שמןזית"                 -> painterResource(Res.drawable.olive_oil)
            "שמן"                     -> painterResource(Res.drawable.kanola_oil)
            "פלפלשחור"               -> painterResource(Res.drawable.black_pepper)
            "תמציתוניל", "וניל"      -> painterResource(Res.drawable.vanil)
            else -> null
        }
    }.getOrNull()
}
