package com.example.lifenotescompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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


@Composable
fun TermsScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)
    ){
        Column(modifier = Modifier.fillMaxSize()){
            HeaderTextComponent(value = stringResource(id = R.string.terms_header))
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .padding(10.dp)
            ){
                NormalTextComponent(value = "1. yakin mau buat akun?\n" +
                        "2. apakah anda bersedia data anda dilihat oleh orang lain?\n" +
                        "3. kami akan melakukan pengecekan data anda")
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