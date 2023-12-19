@file:Suppress("UNUSED_EXPRESSION")

package com.example.lifenotescompose.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.lifenotescompose.R
import com.example.lifenotescompose.app.ButtonComponent
import com.example.lifenotescompose.app.CalenderComponent
import com.example.lifenotescompose.app.DailyFieldComponent
import com.example.lifenotescompose.app.HeaderTextComponent
import com.example.lifenotescompose.data.EventData
import com.example.lifenotescompose.data.EventParamPost
import com.example.lifenotescompose.data.EventRespModel
import com.example.lifenotescompose.data.UsersData
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.BottomNavGraph
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.navigation.Screens
import com.example.lifenotescompose.ui.theme.Black
import com.example.lifenotescompose.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(index: Int) {

    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            AppToolbar()
        },
        bottomBar = {
            BottomNav(navController = navController, index = index)
        }
    ) {
        it
        if (index == 0) {
            BottomNavGraph(
                navController = navController,
                startD = Screens.Home.route
            )
        } else {
            BottomNavGraph(
                navController = navController,
                startD = Screens.Calender.route
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name), color = Black)
        },
        actions = {
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = Black
                )
            }
        }
    )
}

@Composable
fun BottomNav(
    index: Int,
    navController: NavHostController
) {
    var selectedIndex by remember {
        mutableIntStateOf(index)
    }
    val list = listOf(
        Screens.Home,
        Screens.Calender
    )
    NavigationBar {
        list.forEachIndexed { index, screens ->
            NavigationBarItem(
                icon = { Icon(imageVector = screens.icon, contentDescription = "") },
                label = { Text(text = screens.title) },
                selected = selectedIndex == index,
                onClick = {
                    navController.navigate(screens.route)
                    selectedIndex = index
                },
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
//    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
 "com.example.lifenotescompose"+ ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }



    val firestore: FirebaseFirestore = Firebase.firestore
    var now by remember { mutableStateOf("") }
    val eventText = remember { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var uid by rememberSaveable { mutableStateOf("") }
    val listData: MutableList<EventData> = ArrayList()
    var event: EventRespModel by remember { mutableStateOf(EventRespModel()) }
    val calendar = Calendar.getInstance()
    calendar.set(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    //metode buat akses localstorage
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    //function yang di jalanin ketika screen di buka tanpa trigger
    LaunchedEffect("1") {
        // cari data yang udh di simpan di local storage dalam case ini kita sudah simpan uid.
        val result = getData("userData", defaultValue = "empty", sharedPreferences)
        Log.d("data Token =>", result)

        if (result != null) {
            //variable uid
            uid = result
            //ini function buat get data di firestore berdasarkan collection dan document id

            //ini buat data user
            val userRef = firestore.collection("users").document(result)
            //ambil data
            val userSnapshot = userRef.get().await()

            if (userSnapshot.exists()) {
                //data nya di jadiin tipe data object
                //contoh data object
//                {
//                    "data" : "nama",
//                    "kelas " : "TI",
//                }

                val data = userSnapshot.toObject<UsersData>()
                firstName = data?.firstName ?: ""
                email = data?.email ?: ""
                Log.d("ini firstName", firstName)
            }

            //ini di buat get data event
            val eventRef = firestore.collection("calendar").document(result)

            //Get dulu

            val eventSnapshot = eventRef.get().await()
            if (eventSnapshot.exists()) {
                val data = eventSnapshot.toObject<EventRespModel>()
                Log.d("isi Data", data.toString())
//                var dataItem = EventRespModel(data!!)

                for (items in data!!.event) {
                    Log.d("data di list", items.event)
                }
                event = data
            }

        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    )
    {
        Column {
            HeaderTextComponent(value = "Halo $firstName, Welcome back ")
            Spacer(modifier = Modifier.height(20.dp))
            DailyFieldComponent(
                labelValue = stringResource(id = R.string.hows),
                painterResource = painterResource(id = R.drawable.message),
                onTextSelected = {
                    eventText.value = it
                },
                controller = eventText
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
            ) {
                if (capturedImageUri.path?.isNotEmpty() == true) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)

                            .padding(16.dp, 8.dp),
                        painter = rememberAsyncImagePainter(capturedImageUri),
                        contentDescription = null
                    )
                }
            }
            if(capturedImageUri.path?.isNotEmpty() == true){
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        // Request a permission
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                , enabled = false){
                    Icon(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = null,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
            }else {
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(
                    onClick = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            // Request a permission
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = null,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            ButtonComponent(
                value = stringResource(id = R.string.post),
                onButtonClicked = {
                    val current = LocalDateTime.now()
                    val storageRef = FirebaseStorage.getInstance().reference
                    val imageRef = storageRef.child("images/${current}.jpg")
                    val uploadTask = imageRef.putFile(capturedImageUri)

                    uploadTask.addOnSuccessListener {
                        // Image upload successful
                        imageRef.downloadUrl.addOnSuccessListener {
                                Uri ->
                            val result = Uri.toString()
                            Log.d("ini url",result)

                            val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                            now = formatter.format(calendar.timeInMillis)

                            if (event.event.isNotEmpty()) {
                                listData.addAll(event.event)
                            }

                            listData.add(
                                EventData(
                                    event = eventText.value,
                                    date = now,
                                    email = email,
                                    imageUrl = result
                                )
                            )

                            val data = EventParamPost(
                                listData
                            )
                            writeDataToFirestore(
                                data, uid, firestore
                            )
                        }
                    }.addOnFailureListener {
                        Log.d("Upload Image Failed", "Failed")
                    }
                },
                isEnabled = eventText.value.isNotEmpty()
            )

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalenderScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CalenderComponent()
        }
    }
}


fun writeDataToFirestore(
    eventParamPost: EventParamPost, uid: String, firestore: FirebaseFirestore
) {

    val userRef = firestore.collection("calendar").document(uid)
    Log.d("Kepanggil", "test")
    //Add
    userRef.set(
        eventParamPost
    )
    .addOnSuccessListener {
        Log.d("success Input", "Success")
    }
    .addOnFailureListener {
        Log.d("Failed Input", "Failed")
        // Error occurred while saving user data
//        }
    }
}


//Function Create Image
@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        // directory
        externalCacheDir
    )
}
