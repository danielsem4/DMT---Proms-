package com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room


import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class Room(val displayName: StringResource, val imageRes: DrawableResource) {
    Bedroom( Resources.String.bedroom, Resources.Icon.bedroomBlockIcon),
    LivingRoom( Resources.String.living_room, Resources.Icon.salonBlockIcon),
    Kitchen( Resources.String.kitchen, Resources.Icon.kitchenBlockIcon)
}
