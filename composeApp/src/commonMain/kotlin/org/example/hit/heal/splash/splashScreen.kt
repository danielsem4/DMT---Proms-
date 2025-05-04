package org.example.hit.heal.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.med_presc
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
class SplashScreen(): Screen {
    @Composable
    override fun Content() {
        SplashScreen(
            onNavigateToHome = { /* TODO: Implement navigation to Home */ },
            onNavigateToLogin = { /* TODO: Implement navigation to Login */ }
        )
    }
}
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