package com.parim.weeklycalendar.model

data class RequestDTO (
    private val apiKey:String, val startDate: String, private val endDate: String)