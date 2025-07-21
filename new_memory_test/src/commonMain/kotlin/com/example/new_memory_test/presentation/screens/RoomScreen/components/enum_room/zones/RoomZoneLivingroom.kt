package com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.zones

import com.example.new_memory_test.presentation.screens.RoomScreen.data.RoomZone

enum class RoomZoneLivingRoom (override val displayName: String) : RoomZone {
    Sofa("Sofa"),
    Table("Table"),
    Shelf("Shelf"),
    Other("Other")
}