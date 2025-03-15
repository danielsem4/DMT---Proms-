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
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.med_presc
import org.example.EmailTextField
import org.example.PasswordTextField
import org.example.hit.heal.Home.AuthViewModel
import org.example.hit.heal.Home.LoginState
import org.example.hit.heal.core.presentation.BaseScreen

import org.jetbrains.compose.resources.painterResource









@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()


    BaseScreen(title = "Login") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

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

            Spacer(modifier = Modifier.height(100.dp))


            // Login Button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(33.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6FCF97))
            ) {
                Text("Login", fontSize = 20.sp, color = Color.White)
            }
            if (loginState is LoginState.Error) {
                androidx.compose.material3.Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            LaunchedEffect(loginState) {
                if (loginState is LoginState.Success) {
                    onLoginSuccess()
                }
            }
        }
    }
}