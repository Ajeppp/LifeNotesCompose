package com.example.lifenotescompose.screen

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.app.ButtonComponent
import com.example.lifenotescompose.app.CheckboxComponent
import com.example.lifenotescompose.app.HeaderTextComponent
import com.example.lifenotescompose.app.LoginComponent
import com.example.lifenotescompose.app.NormalTextComponent
import com.example.lifenotescompose.app.PasswordComponent
import com.example.lifenotescompose.app.TextFieldComponent
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.lifenotescompose.data.RegisParamPost

@Composable
fun SignUp() {
    val firestore: FirebaseFirestore = Firebase.firestore
    //ini untuk textfield
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val check = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {

            NormalTextComponent(value = stringResource(id = R.string.helo))
            HeaderTextComponent(value = stringResource(id = R.string.create))
            Spacer(modifier = Modifier.height(15.dp))

            //Nama Depan
            TextFieldComponent(
                labelValue = stringResource(id = R.string.first_name),
                painterResource(id = R.drawable.profile),
                onTextSelected = {
                    firstName.value = it
                },
                controller = firstName
            )

            //Nama Belakang
            TextFieldComponent(
                labelValue = stringResource(id = R.string.last_name),
                painterResource = painterResource(id = R.drawable.profile),
                onTextSelected = {
                    lastName.value = it
                },
                controller = lastName
            )

            //Email
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
                    password.value = it
                },
                passwordVal = password,
            )

            CheckboxComponent(value = stringResource(id = R.string.terms),
                onTextSelected = {
                    AppRouter.navigateTo(Screen.Terms)
                },
                onCheckedChanged = {
                    check.value = it
                },
                isChecked = check.value
            )

            Spacer(modifier = Modifier.height(80.dp))
            ButtonComponent(
                value = stringResource(id = R.string.register),
                isEnabled = firstName.value.isNotEmpty() && lastName.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && check.value,
                onButtonClicked = {
                    Log.d("user nih om ", "${email.value} ${password.value}")
                    val result = createUserFire(
                        firstName.value,
                        lastName = lastName.value,
                        email = email.value, password = password.value, AppRouter,
                        firestore
                    )
                    Log.d("Data Res", "$result")
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            LoginComponent(tryLogin = true, onTextSelected = {
                AppRouter.navigateTo(Screen.Login)
            })
        }
    }

}

//Function create user ke Firebase
fun createUserFire(
    firstName: String, lastName: String,
    email: String, password: String, route: AppRouter, firestore: FirebaseFirestore
) {
    //Function untuk create data baru (ini cuma auth aja jadi yg dipake email dan password)
    FirebaseAuth
        .getInstance()
        .createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            //kalo berhasil cari uid
            val uid = FirebaseAuth
                .getInstance().currentUser?.uid ?: ""
            //abis itu masukin data lainnya ke model (Regis Param Post)
            val paramsPost = RegisParamPost(firstName, lastName, email, password)
            //ini untuk input data ke firestore berdasarkan uid
            writeToFirestore(
                paramsPost,
                uid, firestore
            )
            route.navigateTo(Screen.Login)
            Log.d(TAG, "isSuccessful: ${task.isSuccessful}")


        }
        .addOnFailureListener {

            Log.d(TAG, "Exception: ${it.message}")
            Log.d(TAG, "Exception: ${it.localizedMessage}")

        }

}

fun writeToFirestore(paramPost: RegisParamPost, uid: String, firestore: FirebaseFirestore) {
    //collection kita buat manual
    //document dapat dri response firebase
    val userRef = firestore.collection("users").document(uid)
    //function untuk create baru
    userRef.set(
        paramPost
    )
        .addOnSuccessListener {
            Log.d("success Input" , "Success")
        }
        .addOnFailureListener {
            Log.d("Failed Input" , "Failed")
            // Error occurred while saving user data
        }

}

@Preview
@Composable
fun PreviewSignUp() {
    SignUp()
}
