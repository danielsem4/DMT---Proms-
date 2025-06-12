package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum.zones

import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.data.RoomZone

enum class RoomZoneKitchen (override val displayName: String) : RoomZone {
    Counter("Counter"),
    Sink("Sink"),
    Table("Table"),
    Other("Other")
}