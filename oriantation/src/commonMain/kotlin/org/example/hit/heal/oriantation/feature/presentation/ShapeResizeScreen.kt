package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.DrawableResource

data class Item(
    val id: Int,
    val resId: DrawableResource,
    var isPlaced: Boolean = false,
    var position: Offset = Offset.Zero
)

class ShapeResizeScreen : Screen {
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
}