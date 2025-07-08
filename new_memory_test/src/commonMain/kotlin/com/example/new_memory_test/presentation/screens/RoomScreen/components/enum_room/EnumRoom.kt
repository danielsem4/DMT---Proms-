package com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room

import dmt_proms.new_memory_test.generated.resources.Res
import dmt_proms.new_memory_test.generated.resources.bedroom_block
import dmt_proms.new_memory_test.generated.resources.kitchen_block
import dmt_proms.new_memory_test.generated.resources.salon_block
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class Room(val displayName: StringResource, val imageRes: DrawableResource) {
    Bedroom( Resources.String.bedroom, Res.drawable.bedroom_block),
    LivingRoom( Resources.String.living_room, Res.drawable.salon_block),
    Kitchen( Resources.String.kitchen, Res.drawable.kitchen_block)
}
