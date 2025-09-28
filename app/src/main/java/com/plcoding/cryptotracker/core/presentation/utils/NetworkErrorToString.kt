package com.plcoding.cryptotracker.core.presentation.utils

import android.content.Context
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.domain.utils.NetworkError

fun NetworkError.toString(context: Context): String{
val resID = when(this){
    NetworkError.REQUEST_TIMEOUT -> R.string.reqest_timeout
    NetworkError.TOO_MANY_REQUEST -> R.string.too_many_requst
    NetworkError.NO_INTERNET -> R.string.no_internet
    NetworkError.SERVER_ERROR -> R.string.server_error
    NetworkError.SERIALIZATION -> R.string.serialization
    NetworkError.UNKNOWN -> R.string.unknown
}
    return  context.getString(resID)
}