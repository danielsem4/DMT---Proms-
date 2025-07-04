import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.med_presc
import org.example.EmailTextField
import org.example.PasswordTextField
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.login

import org.example.hit.heal.home.HomeScreen
import org.example.hit.heal.login.LoginViewModel
import org.example.hit.heal.presentaion.screens.BaseScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class LoginScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginViewModel: LoginViewModel = koinViewModel()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val snackbarHostState = remember { SnackbarHostState() }
        val isLoading by loginViewModel.isLoading.collectAsState()

        var msgResID: StringResource? by remember { mutableStateOf(null) }
        var message: String by remember { mutableStateOf("") }

        val fillMsg = stringResource(Resources.String.fill_fields)
        val loginSuccessMsg = stringResource(Resources.String.loginSuccess)

        // Convert StringResource to String when msgResID changes
        msgResID?.let { resId ->
            message = stringResource(resId)
        }

        // Show message if any
        LaunchedEffect(message) {
            if (message.isNotEmpty()) {
                snackbarHostState.showSnackbar(message)
                message = ""
            }
        }

        BaseScreen(title = stringResource(login)) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))

                    // Login Image
                    Image(
                        painter = painterResource(Res.drawable.med_presc),
                        contentDescription = "Login Illustration",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 20.dp)
                    )

                    EmailTextField(
                        email = email,
                        onValueChange = { email = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PasswordTextField(
                        password = password,
                        onValueChange = { password = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show message in a Text component
                    if (message.isNotEmpty())
                        Text(
                            text = message,
                            color = if (loginViewModel.isLoggedIn.value) Color(0xFF4CAF50) else Color(
                                0xFFE53935
                            ),
                            modifier = Modifier.padding(8.dp)
                        )

                    Spacer(modifier = Modifier.weight(1f))

                    // Login Button
                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                loginViewModel.login(
                                    email, password,
                                    onLoginSuccess = {
                                        message = loginSuccessMsg
                                        navigator.push(HomeScreen())
                                    },
                                    onLoginFailed = { errorResID -> msgResID = errorResID }
                                )
                            } else {
                                message = fillMsg
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        shape = RoundedCornerShape(33.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6FCF97)),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(stringResource(login), fontSize = 20.sp, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // SnackbarHost positioned at the bottom
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}