package com.example.lifenotescompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.ui.theme.Primary
import com.example.lifenotescompose.ui.theme.Purple80
import com.example.lifenotescompose.ui.theme.PurpleGrey80

@Composable
fun Begin() {
    val type = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey80),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(
            painter = painterResource(id = R.drawable.lifenotes),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 100.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "LifeNotes",
            style = (type.displaySmall).copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(16.dp, 8.dp)
        )

        Text(
            text = "Your personal diary and notes app",
            style = type.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(16.dp, 8.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(300.dp))

        Button(
            onClick = {AppRouter.navigateTo(Screen.Login)},
            modifier = Modifier
                .width(200.dp)
                .heightIn(45.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .heightIn(45.dp)
                    .background(
                        brush = Brush.horizontalGradient(listOf(Purple80, Primary)),
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Icons.Default.ArrowForward
            }
        }
    }
}

@Preview

@Composable
fun BeginPreview() {
    Begin()
}