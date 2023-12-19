@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lifenotescompose.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.lifenotescompose.R
import com.example.lifenotescompose.data.EventData
import com.example.lifenotescompose.data.EventParamPost
import com.example.lifenotescompose.data.EventRespModel
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.screen.getData
import com.example.lifenotescompose.ui.theme.Bg
import com.example.lifenotescompose.ui.theme.Primary
import com.example.lifenotescompose.ui.theme.Purple80
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.black),
        textAlign = TextAlign.Center
    )
}


// Event print
@Composable
fun EventComponent(
    appRouter: AppRouter,
    uid: String,
    firestore: FirebaseFirestore,
    value: EventRespModel,
    dateData: String
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogDel by remember { mutableStateOf(false) }
    var selectedData: EventData by remember { mutableStateOf(EventData()) }


    LazyColumn(
    ) {
        items(
            value.event.filter { it.date == dateData }
        ) { item ->
            Log.d("url", item.imageUrl)
            Column {
                if (item.imageUrl != "") {
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Box {
                                Text(
                                    text = item.event,
                                    modifier = Modifier.padding(15.dp),
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontStyle = FontStyle.Normal
                                    ),
                                    color = colorResource(id = R.color.black),
                                    textAlign = TextAlign.Left
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .width(500.dp),
                                    model = item.imageUrl, contentDescription = null
                                )
                                Row {
                                    Button(onClick = {
                                        selectedData = item
                                        showDialog = true
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.edit),
                                            contentDescription = "Icon Edit"
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Button(onClick = {
                                        selectedData = item
                                        showDialogDel = true
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.delete),
                                            contentDescription = "Icon Delete"
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box {
                            Text(
                                text = item.event,
                                modifier = Modifier.padding(15.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal
                                ),
                                color = colorResource(id = R.color.black),
                                textAlign = TextAlign.Left
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                        ) {
                            Row {
                                Button(onClick = {
                                    selectedData = item
                                    showDialog = true
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.edit),
                                        contentDescription = "Icon Edit"
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Button(onClick = {
                                    selectedData = item
                                    showDialogDel = true
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete),
                                        contentDescription = "Icon Delete"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // Call editDialog and deleteDialog composable functions here with the necessary parameters
    if (showDialog) {
        editDialog(appRouter, showDialog, selectedData, EventParamPost(value.event), uid, firestore)
    }
    if (showDialogDel) {
        deleteDialog(
            appRouter,
            showDialogDel,
            selectedData,
            EventParamPost(value.event),
            uid,
            firestore
        )
    }
}


@Composable
fun HeaderTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.black),
        textAlign = TextAlign.Center
    )
}


// Event Field
@Composable
fun DailyFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,

    controller: MutableState<String>
) {
    val maxChar = 1000
    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = { Text(text = labelValue) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Bg,
                unfocusedContainerColor = Bg,
                disabledContainerColor = Bg,
                cursorColor = Purple80,
                focusedBorderColor = Purple80,
                focusedLabelColor = Purple80,
            ),
            value = controller.value,
            onValueChange = {
                if (it.length <= maxChar) {
                    controller.value = it
                    onTextSelected(it)
                }
            },
            leadingIcon = {
                Icon(
                    painter = painterResource,
                    contentDescription = "",
                )
            },
        )
        Text(
            text = "${controller.value.length} / $maxChar characters",
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
        )
    }
}

@Composable
fun TextFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    controller: MutableState<String>
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Bg,
            unfocusedContainerColor = Bg,
            disabledContainerColor = Bg,
            cursorColor = Purple80,
            focusedBorderColor = Purple80,
            focusedLabelColor = Purple80,
        ),
        value = controller.value,
        onValueChange = {
            controller.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "",
            )
        },
    )
}

@Composable
fun PasswordComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    passwordVal: MutableState<String>
) {
    val localFocus = LocalFocusManager.current
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {
            localFocus.clearFocus()
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Bg,
            unfocusedContainerColor = Bg,
            disabledContainerColor = Bg,
            cursorColor = Purple80,
            focusedBorderColor = Purple80,
            focusedLabelColor = Purple80,
        ),
        value = passwordVal.value,
        onValueChange = {
            passwordVal.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = ""
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val desc = if (passwordVisible.value) {
                stringResource(id = R.string.hide)
            } else {
                stringResource(id = R.string.show)
            }

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(imageVector = iconImage, contentDescription = desc)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@Composable
fun CheckboxComponent(
    value: String,
    onTextSelected: (String) -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    isChecked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(55.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onCheckedChanged.invoke(it)
                isChecked
            })
        ClickableComponent(value = value, onTextSelected)
    }
}

// Click terms
@Composable
fun ClickableComponent(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing you accept our "
    val privacy = "Privacy Policy "
    val and = " and "
    val terms = " Term of Use"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = privacy, annotation = privacy)
            append(privacy)
        }
        append(and)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = terms, annotation = terms)
            append(terms)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableComponent", "{$span}")
                if (span.item == terms || span.item == privacy) {
                    onTextSelected(span.item)
                }
            }
    })
}

// Button
@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(45.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Purple80, Primary)),
                    shape = RoundedCornerShape(40.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

// Login
@Composable
fun LoginComponent(tryLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText = if (tryLogin) "Already have an account?" else "Don't have an account?"
    val loginText = if (tryLogin) " Login" else " Register"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        style = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableComponent", "{$span}")
                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }
        })
}


// Calender
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalenderComponent() {
    //CALENDAR
    val calendar = Calendar.getInstance()
    calendar.set(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    DatePicker(state = datePickerState)
    Log.d("date Data", formatter.format(Date(datePickerState.selectedDateMillis!!)).toString())


    //PROSES EVENT DATA
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var uid by rememberSaveable { mutableStateOf("") }
    var event: EventRespModel by remember { mutableStateOf(EventRespModel()) }

    val context = LocalContext.current //ini sementara dibutuhkan

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
            val userRef = firestore.collection("calendar").document(result)
            Log.d("ini lewat", "")

            val userSnapshot = userRef.get().await()
            //ambil data

            if (userSnapshot.exists()) {
                val data = userSnapshot.toObject<EventRespModel>()
                Log.d("isi Data", data.toString())
                for (items in data!!.event) {
                    Log.d("data di list", items.event)
                }
                event = data
                Log.d("len", event.event.size.toString())

                for (items in event!!.event) {
                    Log.d("data di event", items.email)
                }
            }
        }
    }
    EventComponent(
        appRouter = AppRouter,
        uid, firestore,
        value = event,
        dateData = formatter.format(Date(datePickerState.selectedDateMillis!!)).toString()
    )
}


//Function untuk edit
@Composable
fun editDialog(
    appRouter: AppRouter,
    showDialog: Boolean,
    eventData: EventData,
    eventParamPost: EventParamPost, uid: String, firestore: FirebaseFirestore
) {
    val eventText = remember { mutableStateOf("") }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    if (showDialog) {
        Dialog(onDismissRequest = ({
            AppRouter.navigateTo(Screen.HomeCalendar)
        })) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .height(300.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Edit Event")
                    Spacer(modifier = Modifier.height(10.dp))
                    DailyFieldComponent(
                        labelValue = eventData.event,
                        painterResource = painterResource(id = R.drawable.edit),
                        onTextSelected = {
                            eventText.value = it
                        },
                        controller = eventText
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                        Button(
                            modifier = Modifier.width(150.dp),
                            onClick = {
                                var newData = EventData(
                                    event = eventText.value,
                                    email = eventData.email,
                                    date = eventData.date,
                                )

                                var data = eventParamPost.event.filter { it ->
                                    it.event == eventData.event
                                }
                                var index = eventParamPost.event.indexOf(data.first())
                                eventParamPost.event[index] = newData

                                if (eventText.value.isNotEmpty()) {
                                    EditorDeleteDataEvent(
                                        eventParamPost,
                                        uid,
                                        firestore
                                    )
                                } else {
                                    eventParamPost
                                }
                                onBackPressedDispatcher?.onBackPressed()
                            }
                        ) {
                            Text(text = "Save")
                        }
                        Button(
                            modifier = Modifier.width(150.dp),
                            onClick = {
                                if (eventParamPost.event.size == 0) {
                                    onBackPressedDispatcher?.onBackPressed()
                                    AppRouter.navigateTo(Screen.HomeCalendar)
                                } else {
                                    onBackPressedDispatcher?.onBackPressed()
                                }
                                Log.d("ini lewat", "")
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun deleteDialog(
    appRouter: AppRouter,
    showDialogDel: Boolean,
    eventData: EventData,
    eventParamPost: EventParamPost, uid: String, firestore: FirebaseFirestore
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    if (showDialogDel) {
        Dialog(onDismissRequest = ({
            AppRouter.navigateTo(Screen.HomeCalendar)
        })) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .height(300.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                ) {
                    NormalTextComponent(value = "Are you sure want to delete this event ?")
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Button(
                            modifier = Modifier.width(100.dp),
                            onClick = {
                                var data = eventParamPost.event.filter { it ->
                                    it.event == eventData.event
                                }
                                var index = eventParamPost.event.indexOf(data.first())
                                eventParamPost.event.removeAt(index)

                                EditorDeleteDataEvent(
                                    eventParamPost,
                                    uid,
                                    firestore
                                )
                                onBackPressedDispatcher?.onBackPressed()
                            }
                        ) {
                            Text(text = "Delete")
                        }
                        Button(
                            modifier = Modifier.width(100.dp),
                            onClick = {
                                if (eventParamPost.event.size == 0) {
                                    onBackPressedDispatcher?.onBackPressed()
                                    AppRouter.navigateTo(Screen.HomeCalendar)
                                } else {
                                    onBackPressedDispatcher?.onBackPressed()
                                }
                                Log.d("ini lewat", "")
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}


fun EditorDeleteDataEvent(
    eventParamPost: EventParamPost, uid: String, firestore: FirebaseFirestore
) {
    val userRef = firestore.collection("calendar").document(uid)

    //Add
    userRef.set(eventParamPost)
        .addOnSuccessListener {
            Log.d("success Input", "Success")
        }
        .addOnFailureListener {
            Log.d("Failed Input", "Failed")
            // Error occurred while saving user data
        }
}
