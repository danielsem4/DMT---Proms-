package com.example.new_memory_test.presentation.screens.RoomScreen.screen

import androidx.lifecycle.ViewModel
import org.example.hit.heal.core.presentation.Resources

/**
 *
 */

class RoomsViewModel : ViewModel()  {

    val startItems = listOf(
        Resources.Icon.glassesImage,
        Resources.Icon.bookImage,
        Resources.Icon.dressImage,
        Resources.Icon.phoneImage,
        Resources.Icon.keysImage,
        Resources.Icon.walletImage,
        Resources.Icon.bottleImage
    )

    val allItems = listOf(
        Resources.Icon.glassesImage,
        Resources.Icon.bookImage,
        Resources.Icon.dressImage,
        Resources.Icon.phoneImage,
        Resources.Icon.keysImage,
        Resources.Icon.walletImage,
        Resources.Icon.coffeeImage,
        Resources.Icon.backpackIcon,
        Resources.Icon.appIcon,
        Resources.Icon.shoesImages,
        Resources.Icon.recordsIcon,
        Resources.Icon.bottleImage
    )

    fun formatTime(seconds: Int): String {
        val minutesPart = (seconds / 60).toString().padStart(2, '0')
        val secondsPart = (seconds % 60).toString().padStart(2, '0')
        return "$minutesPart:$secondsPart"
    }

}