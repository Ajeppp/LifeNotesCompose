package com.example.lifenotescompose.data

data class RegisParamPost(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var check: Boolean = false
)

data class EventParamPost(
    var event: MutableList<EventData>
)

data class EventRespModel(
    var event: ArrayList<EventData> = arrayListOf()
)

data class EventData(
    var event: String = "",
    var date: String = "",
    var email: String = "",
    var imageUrl : String = "",

)

data class UsersData(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
)