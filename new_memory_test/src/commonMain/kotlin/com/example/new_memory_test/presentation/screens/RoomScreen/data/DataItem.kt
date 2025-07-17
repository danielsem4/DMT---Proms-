package com.example.new_memory_test.presentation.screens.RoomScreen.data

import androidx.compose.ui.geometry.Offset
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import core.data.model.MeasureObjectString
import org.jetbrains.compose.resources.DrawableResource

data class DataItem(
    val id: Int,
    val resId: DrawableResource,
    val isPlaced: Boolean = false,
    val position: Offset = Offset.Zero,
    val room: Room? = null,
    val zone: RoomZone? = null
)

