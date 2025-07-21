package org.example.hit.heal.presentation.medication.presentaion.screens.mainMedication
import MedicationListScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import dmt_proms.composeapp.generated.resources.medication_alarm
import dmt_proms.composeapp.generated.resources.pills
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.presentation.medication.presentaion.screens.MedicationViewModel.MedicationViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class MainMedicationScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<MedicationViewModel>()
            //  LaunchedEffect(Unit){
      //      viewModel.loadEvaluation("medications")
                //  }
        BaseScreen(
            title = stringResource(Resources.String.medications),
            config = ScreenConfig.PhoneConfig,

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(55.dp))

                Button(
                    onClick = {   viewModel.setReport(true)
                        navigator.push(MedicationListScreen(isReport = true))},
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp)
                        .height(65.dp),


                ){
                    Image(
                        painter = painterResource(Res.drawable.pills),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(bottom = 3.dp,top=3.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(text = stringResource(Resources.String.reportMedication), fontSize = 18.sp,  modifier = Modifier.padding(horizontal = 8.dp))

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {   viewModel.setReport(false)
                        navigator.push(MedicationListScreen(isReport = false))},
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp)
                        .height(65.dp),


                    ){
                    Image(
                        painter = painterResource(Res.drawable.medication_alarm),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(bottom = 3.dp,top=3.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(text =stringResource(Resources.String.setMedicationAlarm), fontSize = 18.sp,  modifier = Modifier.padding(horizontal = 8.dp))

                }


            }
        }


    }

}

