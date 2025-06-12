package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.data
import androidx.compose.ui.geometry.Offset
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum.Room
import org.jetbrains.compose.resources.DrawableResource

data class DataItem(
    val id: Int,
    val resId: DrawableResource,
    val isPlaced: Boolean = false,
    val position: Offset = Offset.Zero,
    val room: Room? = null,
    val zone: RoomZone? = null
)

