package com.plcoding.cryptotracker.crypto.presentation.model.coin_detail

import java.text.NumberFormat
import java.util.Locale

data class ValueLabel(
    val value: Float,
    val unite: String
){
    fun formattedd(): String{
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fraction = when{
                value>100 -> 0
                value in  2f..999f->2
                else->3
            }
            maximumIntegerDigits = fraction
            minimumIntegerDigits = 0
        }
        return  "${formatter.format(value)} $unite"
    }
}
