import androidx.compose.foundation.Image
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
import org.example.hit.heal.Home.HomeScreen
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.login.LoginViewModel
import org.jetbrains.compose.resources.painterResource
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
        val message by loginViewModel.message.collectAsState()

        // Show message if any
        LaunchedEffect(message) {
            message?.let {
                snackbarHostState.showSnackbar(message = it)
                loginViewModel.clearMessage()
            }
        }

        BaseScreen(title = "Login") {
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
                message?.let { msg ->
                    Text(
                        text = msg,
                        color = if (loginViewModel.isLoggedIn.value) Color(0xFF4CAF50) else Color(
                            0xFFE53935
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Login Button
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            loginViewModel.login(email, password) {
                                navigator.push(HomeScreen())
                            }
                        } else {
                            loginViewModel.setMessage("Please fill in all fields")
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
                        Text("Login", fontSize = 20.sp, color = Color.White)
                    }
                }

                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}
