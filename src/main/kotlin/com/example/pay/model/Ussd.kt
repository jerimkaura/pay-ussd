package com.example.pay.model

data class Ussd(
    val sessionId: String,
    val serviceCode: String,
    val phoneNumber: String,
    val text : String
)
