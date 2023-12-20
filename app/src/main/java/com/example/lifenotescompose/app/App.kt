package com.example.lifenotescompose.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.screen.Begin
import com.example.lifenotescompose.screen.Login
import com.example.lifenotescompose.screen.MainScreen
import com.example.lifenotescompose.screen.SignUp
import com.example.lifenotescompose.screen.TermsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = AppRouter.currentScreen, label = "") { current ->
            if (current.value == Screen.Begin) {
                Begin()
            }else if (current.value == Screen.SignUp) {
                SignUp()
            } else if (current.value == Screen.Terms) {
                TermsScreen()
            } else if (current.value == Screen.Home) {
                MainScreen(index = 0)
            } 
            else if(current.value == Screen.HomeCalendar){
                MainScreen(index = 1)
            }else {
                Login()
            }
        }
    }
}