package com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.zones
import com.example.new_memory_test.presentation.screens.RoomScreen.data.RoomZone

enum class RoomZoneKitchen (override val displayName: String) : RoomZone {
    Counter("Counter"),
    Sink("Sink"),
    Table("Table"),
    Other("Other")
}