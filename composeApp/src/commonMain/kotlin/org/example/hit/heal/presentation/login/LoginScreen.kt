package org.example.hit.heal.presentation.login

import ContentWithMessageBar
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
import org.example.hit.heal.core.presentation.Resources.String.login
import org.example.hit.heal.core.presentation.Sizes.iconSizeMd
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingXl

import org.example.hit.heal.core.presentation.components.SimpleInputText


import org.example.hit.heal.presentation.medication.BaseScreen
import org.example.hit.heal.presentation.home.HomeScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * LoginScreen is a composable function that represents the login screen of the application.
 * It allows users to enter their email and password to log in.
 */

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginViewModel: LoginViewModel = koinViewModel()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        val isLoading by loginViewModel.isLoading.collectAsState()

        var errorResID: StringResource? by remember { mutableStateOf(null) }

        val fillFields   = stringResource(Resources.String.fill_fields)
        val loginSuccess = stringResource(Resources.String.loginSuccess)

        val messageBarState = rememberMessageBarState()

        errorResID?.let { resId ->
            val text = stringResource(resId)
            LaunchedEffect(text) {
                messageBarState.addError(text)
                errorResID = null
            }
        }

        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM
        ) {
            BaseScreen(title = stringResource(login)) {
                Box(modifier = Modifier.fillMaxSize()) {
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
                            value = email,
                            onValueChange = { email = it },
                            label = stringResource(Resources.String.email),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Resources.Icon.emailIcon),
                                    contentDescription = null,
                                    tint = IconPrimary,
                                    modifier = Modifier.size(iconSizeMd)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            keyboardActions = KeyboardActions(
                                onDone = {  }
                            ),
                        )

                        Spacer(modifier = Modifier.height(spacingMd))

                        SimpleInputText(
                            value = password,
                            onValueChange = { password = it },
                            label = stringResource(Resources.String.password),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Resources.Icon.lockIcon),
                                    contentDescription = null,
                                    tint = IconPrimary,
                                    modifier = Modifier.size(iconSizeMd)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            keyboardActions = KeyboardActions(
                                onDone = {  }
                            ),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        painter = if (passwordVisible) painterResource(Resources.Icon.openEyeIcon) else painterResource(Resources.Icon.closedEyeIcon),
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
                                if (email.isBlank() || password.isBlank()) {
                                    messageBarState.addError(fillFields)
                                } else {
                                    loginViewModel.login(
                                        email,
                                        password,
                                        onLoginSuccess = {
                                            messageBarState.addSuccess(loginSuccess)
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
}