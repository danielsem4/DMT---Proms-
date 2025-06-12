package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum.zones

import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.data.RoomZone

enum class RoomZoneLivingRoom (override val displayName: String) : RoomZone {
    Sofa("Sofa"),
    Table("Table"),
    Shelf("Shelf"),
    Other("Other")
}