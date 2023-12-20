package com.example.lifenotescompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen

@Composable
fun Begin() {
    val type = MaterialTheme.typography

//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Transparent)
//            .padding(28.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//        ){
//            Row(
//                modifier = Modifier
//                    .padding(16.dp, 16.dp),
//            ){
//                Box{
//                    Image(
//                        painter = painterResource(id = R.drawable.lifenotes),
//                        contentDescription = null,
//                        alignment = Alignment.Center
//                    )
//                }
//            }
//            Text(
//                text = "LifeNotes",
//                style = type.headlineLarge,
//                color = Color.Black,
//                modifier = Modifier.padding(16.dp, 8.dp)
//            )
//
//            Text(
//                text = "Your personal diary and notes app",
//                style = type.bodyLarge,
//                color = Color.Black,
//                modifier = Modifier.padding(16.dp, 8.dp, bottom = 16.dp)
//            )
//
//            Button(onClick = { AppRouter.navigateTo(Screen.Login) },
//                modifier = Modifier.padding(16.dp, bottom = 16.dp)) {
//                Text(
//                    text = "Get Started",
//                    style = type.labelMedium,
//                    color = Color.White)
//
//            }
//        }
//    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(
            painter = painterResource(id = R.drawable.lifenotes),
            contentDescription = null,
            modifier = Modifier.width(100.dp).height(100.dp).padding(top = 100.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "LifeNotes",
            style = type.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(16.dp, 8.dp)
        )

        Text(
            text = "Your personal diary and notes app",
            style = type.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(16.dp, 8.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(onClick = { AppRouter.navigateTo(Screen.Login) },
            modifier = Modifier.width(200.dp)
        ){
            Text(
                text = "Get Started",
                style = type.labelMedium,
                color = Color.White
            )
        }
    }

}

//@Preview

//@Composable
//fun BeginPreview() {
//    Begin()
//}