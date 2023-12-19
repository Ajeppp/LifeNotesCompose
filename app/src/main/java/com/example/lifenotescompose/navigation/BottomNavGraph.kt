package com.example.lifenotescompose.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lifenotescompose.screen.CalenderScreen
import com.example.lifenotescompose.screen.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    startD: String
) {
    NavHost(
        navController = navController,
        startDestination = startD,
        builder ={
            composable(Screens.Home.route){
                HomeScreen()
            }
            composable(Screens.Calender.route){
                CalenderScreen()
            }

        }
    )
}

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home: Screens(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Calender: Screens(
        route = "calender",
        title = "Calender",
        icon = Icons.Default.CalendarMonth
    )
}