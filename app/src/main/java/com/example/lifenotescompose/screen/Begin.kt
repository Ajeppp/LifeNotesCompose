package com.example.lifenotescompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen

@Composable
fun Begin() {
    val type = MaterialTheme.typography
    val color = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Row(
            modifier = Modifier
                .padding(16.dp, 16.dp),
        ){
            Box{
                Image(
                    painter = painterResource(id = R.drawable.lifenotes),
                    contentDescription = null
                )
            }
        }
        Text(
            text = "LifeNotes",
            style = type.headlineLarge,
            color = color.primary,
            modifier = Modifier.padding(16.dp, 8.dp)
        )

        Text(
            text = "Your personal diary and notes app",
            style = type.bodyLarge,
            color = color.onSurface,
            modifier = Modifier.padding(16.dp, 8.dp, bottom = 16.dp)
        )

        Button(onClick = { AppRouter.navigateTo(Screen.Login) },
            modifier = Modifier.padding(16.dp, bottom = 16.dp)) {
            Text(
                text = "Get Started",
                style = type.labelMedium, 
                color = color.onPrimary)

        }
    }
}

@Preview
@Composable
fun BeginPreview() {
    Begin()
}