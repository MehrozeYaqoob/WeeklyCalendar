package com.parim.weeklycalendar.model

data class RequestDTO (
    private val apiKey:String, private val startDate: String, private val endDate: String)