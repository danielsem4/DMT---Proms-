package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.screenshots

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum.Room
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Composable
//fun AutoSwitchRooms(
//    roomButtons: List<Room>,
//    onRoomSelected: (Room) -> Unit,
//    onDone: () -> Unit // ← чтобы можно было отключить после завершения
//) {
//    LaunchedEffect(Unit) {
//        roomButtons.forEach { room ->
//            onRoomSelected(room)
//            delay(1000)
//        }
//        onDone()
//    }
//}