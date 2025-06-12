package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum

import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.bedroom_block
import dmt_proms.memorytest.core.generated.resources.kitchen_block
import dmt_proms.memorytest.core.generated.resources.salon_block
import org.jetbrains.compose.resources.DrawableResource

enum class Room(val displayName: String, val imageRes: DrawableResource) {
    Bedroom("חדר שינה", Res.drawable.bedroom_block),
    LivingRoom("סלון", Res.drawable.salon_block),
    Kitchen("מטבח", Res.drawable.kitchen_block)
}
