package com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room

import dmt_proms.new_memory_test.generated.resources.Res
import dmt_proms.new_memory_test.generated.resources.bedroom_block
import dmt_proms.new_memory_test.generated.resources.kitchen_block
import dmt_proms.new_memory_test.generated.resources.salon_block
import org.jetbrains.compose.resources.DrawableResource

enum class Room(val displayName: String, val imageRes: DrawableResource) {
    Bedroom("חדר שינה", Res.drawable.bedroom_block),
    LivingRoom("סלון", Res.drawable.salon_block),
    Kitchen("מטבח", Res.drawable.kitchen_block)
}
