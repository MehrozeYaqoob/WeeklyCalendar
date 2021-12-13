package com.parim.weeklycalendar.sealed

sealed class ErrorEvents(open val message: String){
    data class ErrorFetchingRemoteData(override val message: String): ErrorEvents(message)
    data class ErrorSavingRemoteData(override val message: String): ErrorEvents(message)
    data class ErrorRetrievingLocalData(override val message: String): ErrorEvents(message)
}
