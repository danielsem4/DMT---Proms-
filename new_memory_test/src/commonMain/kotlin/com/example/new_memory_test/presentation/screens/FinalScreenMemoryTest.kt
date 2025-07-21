package com.example.new_memory_test.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.end
import org.example.hit.heal.core.presentation.Resources.String.exit
import org.example.hit.heal.core.presentation.Resources.String.sentSuccessfully
import org.example.hit.heal.core.presentation.Resources.String.thanksCoffe
import org.example.hit.heal.core.presentation.Resources.String.unexpectedError
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.core.presentation.utils.animations.SuccessAnimation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class FinalScreenMemoryTest : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ViewModelMemoryTest = koinViewModel()
        val snackbarHostState = remember { SnackbarHostState() }
        val uploadStatus by viewModel.uploadStatus.collectAsState()

        val successMessage = stringResource(sentSuccessfully)
        val unexpectedErrorMessage = stringResource(unexpectedError)

        //Check if upload is done
        LaunchedEffect(uploadStatus) {
            uploadStatus?.let { result ->
                result.onSuccess {
                    snackbarHostState.showSnackbar(successMessage)
                }.onFailure { error ->
                    snackbarHostState.showSnackbar(error.message ?: unexpectedErrorMessage)
                }
            }
        }

        //Upload all Images
        LaunchedEffect(Unit) {
            viewModel.uploadAllImages()
        }

        BaseScreen(
            title = stringResource(end),
            config = ScreenConfig.TabletConfig,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(45.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(radiusMd))
                                .border(
                                    width = 2.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(radiusMd)
                                )
                        ) {
                            Text(
                                text = stringResource(thanksCoffe),
                                color = primaryColor,
                                fontSize = 35.sp,
                                fontWeight = Bold,
                                lineHeight = 40.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                        }

                        SuccessAnimation(modifier = Modifier.size(100.dp))
                    }
                    RoundedButton(
                        text = stringResource(exit),
                        modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp),
                        onClick = {
                            navigator?.popUntilRoot()
                            viewModel.reset()
                        }
                        , enabled = uploadStatus != null
                    )

                }
            },
        )

        RegisterBackHandler(this)
        {
            navigator?.popUntilRoot()
        }
    }
}