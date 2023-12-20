package com.example.lifenotescompose.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    object Begin: Screen()
    object SignUp: Screen()
    object Terms: Screen()
    object Login: Screen()
    object Home: Screen()
    object HomeCalendar: Screen()
}

object AppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Begin)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}