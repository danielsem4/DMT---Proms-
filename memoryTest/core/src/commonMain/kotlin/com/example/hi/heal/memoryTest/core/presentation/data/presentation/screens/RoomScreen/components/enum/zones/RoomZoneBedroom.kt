package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum.zones

import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.data.RoomZone

enum class RoomZoneBedroom (override val displayName: String) : RoomZone {
    ChestOfDrawers("ChestOfDrawers"),
    Hanger("Hanger"),
    Wardrobe("Wardrobe"),
    Bed("Bed"),
    BedsideTable("BedsideTable"),
    ChestUnderFrames("ChestUnderFrames"),
    Other("Other")
}