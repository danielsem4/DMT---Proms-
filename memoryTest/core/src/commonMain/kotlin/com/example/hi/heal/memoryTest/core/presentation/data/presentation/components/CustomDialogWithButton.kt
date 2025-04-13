package com.example.hi.heal.memoryTest.core.presentation.data.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.effects.RipplePulseEffect
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.call_accept
import dmt_proms.memorytest.core.generated.resources.call_remove
import org.jetbrains.compose.resources.painterResource

@Composable
fun CallButtonsWithDialog() {
    var showAcceptDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            RipplePulseEffect(
                modifier = Modifier.fillMaxSize(),
                color = primaryColor
            )

            CircleWithPipeImage(
                imagePainter = painterResource(Res.drawable.call_accept),
                color = primaryColor,
                onClick = { showAcceptDialog = true }
            )
        }

        Spacer(modifier = Modifier.width(200.dp))

        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            RipplePulseEffect(
                modifier = Modifier.fillMaxSize(),
                color = Color.Red
            )

            CircleWithPipeImage(
                imagePainter = painterResource(Res.drawable.call_remove),
                color = Color.Red,
                onClick = { showRejectDialog = true }
            )
        }
    }

    // Диалог при принятии
        // if (showAcceptDialog) {
   //     CustomDialog(
   //         onDismiss = { showAcceptDialog = false },
   //         onNextClick = {
   //             showAcceptDialog = false
                //             // логика по нажатию "далее"
                //         }
                //     )
   // }
        //
        // // Диалог при отклонении
        // if (showRejectDialog) {
   //     CustomDialog(
   //         onDismiss = { showRejectDialog = false },
   //         onNextClick = {
   //             showRejectDialog = false
                //             // логика по нажатию "далее"
                //         }
                //     )
   // }
}