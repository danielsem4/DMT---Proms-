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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import coil3.compose.AsyncImage
import core.data.storage.Storage
import core.util.PrefKeys
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.med_presc
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.example.hit.heal.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    storage: Storage = koinInject()
) {

    // Use a safe approach to image loading
    val defaultImage = painterResource(Res.drawable.med_presc)

    val isLoggedIn by storage
        .getAsFlow(PrefKeys.clinicId)
        .map { it != null }
        .collectAsState(initial = false)

    // show logo / text
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        if (isLoggedIn) Image(
            painter = defaultImage,
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        ) else Text("Welcome")
    }

    LaunchedEffect(isLoggedIn) {
        delay(2000)
        if (isLoggedIn) onNavigateToHome() else onNavigateToLogin()
    }
}