package com.example.new_memory_test.presentation.screens.ScheduleInformationScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.ScheduleInformationScreen.components.ActivityItem
import com.example.new_memory_test.presentation.screens.ScheduleScreen.screen.ScheduleScreen
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.buttonHeightMd
import org.example.hit.heal.core.presentation.Sizes.spacing8Xl
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.widthXl
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


class ScheduleInformationScreen(val pageNumber: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        //Save in one side and don't depend on language
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        BaseScreen(title = stringResource(Resources.String.build_schedule),  config = ScreenConfig.TabletConfig,topRightText = "$pageNumber/6" , content =  {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacingLg),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Resources.String.build_schedule),
                    fontSize = EXTRA_MEDIUM_LARGE,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(spacingMd)
                )

                Spacer(modifier = Modifier.height(spacingMd))

                //Only text + image (instruction)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(spacingMd))
                        .background(Color.White)
                        .padding(spacingMd)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(spacingMd)) {
                        ActivityItem(
                            text = stringResource(Resources.String.dumbbell_circle_text),
                            icon = painterResource(Resources.Icon.dumbbellScheduleIcon)
                        )
                        ActivityItem(
                            text = stringResource(Resources.String.stethoscope_circle_text),
                            icon = painterResource(Resources.Icon.stethascopeScheduleIcon)
                        )
                        ActivityItem(
                            text = stringResource(Resources.String.book_circle_text),
                            icon = painterResource(Resources.Icon.bookScheduleIcon)
                        )
                        ActivityItem(
                            text = stringResource(Resources.String.coffee_circle_text),
                            painterResource(Resources.Icon.coffeeScheduleIcon)
                        )
                        ActivityItem(
                            text = stringResource(Resources.String.lecturer_circle_text),
                            icon = painterResource(Resources.Icon.teachScheduleIcon)
                        )
                        ActivityItem(
                            text = stringResource(Resources.String.move_circle_text),
                            icon = painterResource(Resources.Icon.moveScheduleIcon)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacingLg))

                Button(
                    onClick = {
                        navigator.push(ScheduleScreen(pageNumber = 5))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                    shape = RoundedCornerShape(30),
                    modifier = Modifier
                        .defaultMinSize(minWidth = spacing8Xl)
                        .width(widthXl)
                        .height(buttonHeightMd)
                ) {
                    Text(
                        stringResource(Resources.String.start),
                        fontSize = EXTRA_MEDIUM,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Color.Companion.White
                    )
                }
            }
        })
            RegisterBackHandler(this)
            {
                navigator.popUntilRoot()
            }


        }
    }

}
