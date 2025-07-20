package com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.zones

import com.example.new_memory_test.presentation.screens.RoomScreen.data.RoomZone

enum class RoomZoneBedroom (override val displayName: String) : RoomZone {
    ChestOfDrawers("ChestOfDrawers"),
    Hanger("Hanger"),
    Wardrobe("Wardrobe"),
    Bed("Bed"),
    BedsideTable("BedsideTable"),
    Other("Other")
}