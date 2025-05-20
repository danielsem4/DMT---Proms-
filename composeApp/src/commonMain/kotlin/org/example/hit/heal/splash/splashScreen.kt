package org.example.hit.heal.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.med_presc
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    // Use safe non-suspend methods only
    val isLoggedIn = remember { false }

    // Use a safe approach to image loading
    val defaultImage = painterResource(Res.drawable.med_presc)

    LaunchedEffect(Unit) {
        try {
            delay(2000)
            if (isLoggedIn) {
                onNavigateToHome()
            } else {
                onNavigateToLogin()
            }
        } catch (e: Exception) {
            println("Navigation error: ${e.message}")
            // Fallback navigation
            onNavigateToLogin()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoggedIn) {
            // Use Image instead of AsyncImage to avoid potential Coil issues
            Image(
                painter = defaultImage,
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
//            with coil
//        if (isLoggedIn) {
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = "Clinic Logo",
//                modifier = Modifier.size(200.dp)
//            )
        } else {
            Text(
                text = "Welcome",
                fontSize = 24.sp
            )
        }
    }
}