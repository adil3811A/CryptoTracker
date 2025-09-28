package com.plcoding.cryptotracker.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val id: String,
    val rank: String,               // was Int
    val name: String,
    val symbol: String,
    val marketCapUsd: String,       // was Double
    @SerialName("priceUsd")
    val priceUsd: String,           // rename if JSON key differs
    val changePercent24Hr: String   // was Double
)