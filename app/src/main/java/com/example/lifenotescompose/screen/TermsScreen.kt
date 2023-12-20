package com.example.lifenotescompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.app.ButtonComponent
import com.example.lifenotescompose.app.HeaderTextComponent
import com.example.lifenotescompose.app.NormalTextComponent
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.navigation.SystemBackHandler
import com.example.lifenotescompose.ui.theme.PurpleGrey80


@Composable
fun TermsScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = PurpleGrey80)
        .padding(16.dp)

    ){
        Column(modifier = Modifier.padding(16.dp)){
            HeaderTextComponent(value = stringResource(id = R.string.terms_header))
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(10.dp)
            ){
                NormalTextComponent(value = "We do not collect or share any personal " +
                        "information with third parties. All data stored locally on your device. " +
                        "By using this app, you agree to our Privacy Policy. If you have any concerns " +
                        "or questions, please email us at LifeNotes@gmail.com\n" + stringResource(id = R.string.term_of_use))
            }

            Spacer(modifier = Modifier.padding(8.dp))
            ButtonComponent(value = "submit", onButtonClicked = {
                AppRouter.navigateTo(Screen.SignUp)
            }, isEnabled = true)
        }
    }
    SystemBackHandler {
        AppRouter.navigateTo(Screen.SignUp)
    }
}

@Preview
@Composable
fun PreviewTermsScreen() {
    TermsScreen()
}