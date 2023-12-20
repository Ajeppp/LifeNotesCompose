package com.example.lifenotescompose.screen

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.app.ButtonComponent
import com.example.lifenotescompose.app.HeaderTextComponent
import com.example.lifenotescompose.app.LoginComponent
import com.example.lifenotescompose.app.NormalTextComponent
import com.example.lifenotescompose.app.PasswordComponent
import com.example.lifenotescompose.app.TextFieldComponent
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.navigation.SystemBackHandler
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Login() {
    val context = LocalContext.current

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    //ini untuk textfield
    val email = remember { mutableStateOf("") }
    val passwordData = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding()
        ) {
            HeaderTextComponent(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.message),
                onTextSelected = {
                    email.value = it
                },
                controller = email
            )
            PasswordComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.lock),
                onTextSelected = {
                    passwordData.value = it
                },

                passwordVal = passwordData
            )
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(
                value = stringResource(id = R.string.login),
                isEnabled = email.value.isNotEmpty() && passwordData.value.isNotEmpty(),
                onButtonClicked = {
                    //panggil function login
                    if(email.value.isNotEmpty() && passwordData.value.isNotEmpty()){
                        loginUser(email.value, passwordData.value, sharedPreferences, AppRouter)

                    }else{
                        Log.d(ContentValues.TAG, "Exception: ${email.value}")
                        Log.d(ContentValues.TAG, "Exception: ${passwordData.value}")
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            LoginComponent(tryLogin = false, onTextSelected = {
                AppRouter.navigateTo(Screen.SignUp)
            })
        }
    }

    SystemBackHandler {
        AppRouter.navigateTo(Screen.SignUp)
    }
}

//ini function login ke firebase
fun loginUser(
    email: String, password: String,
    sharedPreferences: SharedPreferences,
    route: AppRouter
) {
    //shared buat simpen data ke local

    //ini function firebase untuk login
    FirebaseAuth
        .getInstance().signInWithEmailAndPassword(email, password)

        .addOnCompleteListener { task ->
            //data untuk dapetin uid
            val data = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//bikin if data ada atau tidak
            
            //ketika success navigasi ke home
            route.navigateTo(Screen.Home)
            //save data login (response nya) ke local storage (patokannya adalah key)
            saveData("userData", data, sharedPreferences)
            Log.d(ContentValues.TAG, "isSuccessful: ${task.isSuccessful}")
        }
        .addOnFailureListener {

            Log.d(ContentValues.TAG, "Exception: ${it.message}")
            Log.d(ContentValues.TAG, "Exception: ${it.localizedMessage}")

        }

}

//function untuk ke Local Storage
fun saveData(key: String, value: String, sharedPreferences: SharedPreferences) {
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun getData(key: String, defaultValue: String, sharedPreferences: SharedPreferences): String {
    return sharedPreferences.getString(key, defaultValue) ?: defaultValue
}

@Preview
@Composable
fun PreviewLoginScreen() {
    Login()
}