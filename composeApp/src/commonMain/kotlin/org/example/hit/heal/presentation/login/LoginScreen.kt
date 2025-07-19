package org.example.hit.heal.presentation.login

import ToastMessage
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.med_presc
import org.example.hit.heal.core.presentation.ButtonPrimary
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.IconPrimary
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.email
import org.example.hit.heal.core.presentation.Resources.String.fill_fields
import org.example.hit.heal.core.presentation.Resources.String.login
import org.example.hit.heal.core.presentation.Resources.String.password
import org.example.hit.heal.core.presentation.Sizes.iconSizeMd
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingXl
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.SimpleInputText
import org.example.hit.heal.presentation.home.HomeScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * LoginScreen is a composable function that represents the login screen of the application.
 * It allows users to enter their email and password to log in.
 */

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginViewModel: LoginViewModel = koinViewModel()

        var emailText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        val isLoading by loginViewModel.isLoading.collectAsState()

        // Toast state
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }

        val fillFields = stringResource(fill_fields)
        val loginSuccess = stringResource(Resources.String.loginSuccess)

        var errorResID: StringResource? by remember { mutableStateOf(null) }

        errorResID?.let { resId ->
            val text = stringResource(resId)
            LaunchedEffect(text) {
                toastMessage = text
                toastType = ToastType.Error
                errorResID = null
            }
        }

        BaseScreen(title = stringResource(login)) {
            Box(modifier = Modifier.fillMaxSize()) {

                // ToastMessage shown conditionally
                toastMessage?.let { msg ->
                    ToastMessage(
                        message = msg,
                        type = toastType,
                        alignUp = false,
                        onDismiss = { toastMessage = null }
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacingMd)
                ) {
                    Spacer(modifier = Modifier.height(spacingXl))

                    Image(
                        painter = painterResource(Res.drawable.med_presc),
                        contentDescription = "Login Illustration",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = paddingLg)
                    )

                    SimpleInputText(
                        value = emailText,
                        onValueChange = { emailText = it },
                        label = stringResource(email),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Resources.Icon.emailIcon),
                                contentDescription = null,
                                tint = IconPrimary,
                                modifier = Modifier.size(iconSizeMd)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        keyboardActions = KeyboardActions(onDone = { }),
                    )

                    Spacer(modifier = Modifier.height(spacingMd))

                    SimpleInputText(
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        label = stringResource(password),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Resources.Icon.lockIcon),
                                contentDescription = null,
                                tint = IconPrimary,
                                modifier = Modifier.size(iconSizeMd)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        keyboardActions = KeyboardActions(onDone = { }),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = if (passwordVisible)
                                        painterResource(Resources.Icon.openEyeIcon)
                                    else
                                        painterResource(Resources.Icon.closedEyeIcon),
                                    contentDescription = null,
                                    modifier = Modifier.size(iconSizeMd),
                                    tint = Color.Black
                                )
                            }
                        },
                        visualTransformation = !passwordVisible,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (emailText.isBlank() || passwordText.isBlank()) {
                                toastMessage = fillFields
                                toastType = ToastType.Warning
                            } else {
                                loginViewModel.login(
                                    emailText,
                                    passwordText,
                                    onLoginSuccess = {
                                        toastMessage = loginSuccess
                                        toastType = ToastType.Success
                                        navigator.push(HomeScreen())
                                    },
                                    onLoginFailed = { resId ->
                                        errorResID = resId
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        shape = RoundedCornerShape(33.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonPrimary),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(iconSizeMd)
                            )
                        } else {
                            Text(
                                text = stringResource(login),
                                fontSize = EXTRA_MEDIUM,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(spacingMd))
                }
            }
        }
    }
}