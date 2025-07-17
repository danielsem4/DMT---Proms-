package com.example.new_memory_test.presentation.screens.RoomScreen.components.zonePosition

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.zones.RoomZoneBedroom
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.zones.RoomZoneKitchen
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.zones.RoomZoneLivingRoom
import com.example.new_memory_test.presentation.screens.RoomScreen.data.RoomArea
import com.example.new_memory_test.presentation.screens.RoomScreen.data.RoomZone

fun getZoneForPosition(
    position: Offset,
    roomPosition: Offset,
    roomSize: IntSize,
    room: Room
): RoomZone {
    val localX = position.x - roomPosition.x
    val localY = position.y - roomPosition.y
    val width = roomSize.width.toFloat()
    val height = roomSize.height.toFloat()

    return when (room) {
        Room.Bedroom -> {
            val roomZones = listOf(
                RoomArea(RoomZoneBedroom.Hanger, Rect(width * 0.08f, height * 0.20f, width * 0.27f, height * 0.55f)),
                RoomArea(RoomZoneBedroom.ChestOfDrawers, Rect(0f,height * 0.20f, width * 0.20f, height * 0.50f)),
                RoomArea(RoomZoneBedroom.Wardrobe, Rect(width * 0.25f, 0f, width * 0.50f, height * 0.60f)),
                RoomArea(RoomZoneBedroom.Bed, Rect(width * 0.40f, height * 0.20f, width * 0.90f, height * 1.0f)),
                RoomArea(RoomZoneBedroom.BedsideTable, Rect(width * 0.90f, height * 0.40f, width * 1.0f, height * 0.70f))
            )
            roomZones.firstOrNull { it.rect.contains(Offset(localX, localY)) }?.zone ?: RoomZoneBedroom.Other
        }
        Room.Kitchen -> {
            val roomZones = listOf(
                RoomArea(RoomZoneKitchen.Counter, Rect(0f, 0f, width * 0.30f, height * 0.40f)),
                RoomArea(RoomZoneKitchen.Sink, Rect(width * 0.30f, 0f, width * 0.50f, height * 0.40f)),
                RoomArea(RoomZoneKitchen.Table, Rect(width * 0.50f, height * 0.40f, width * 1.0f, height * 1.0f))
            )
            roomZones.firstOrNull { it.rect.contains(Offset(localX, localY)) }?.zone ?: RoomZoneKitchen.Other
        }
        Room.LivingRoom -> {
            val roomZones = listOf(
                RoomArea(RoomZoneLivingRoom.Sofa, Rect(0f, height * 0.50f, width * 0.40f, height * 1.0f)),
                RoomArea(RoomZoneLivingRoom.Table, Rect(width * 0.40f, height * 0.50f, width * 0.70f, height * 1.0f)),
                RoomArea(RoomZoneLivingRoom.Shelf, Rect(width * 0.70f, 0f, width * 1.0f, height * 0.50f))
            )
            roomZones.firstOrNull { it.rect.contains(Offset(localX, localY)) }?.zone ?: RoomZoneLivingRoom.Other
        }
    }
}