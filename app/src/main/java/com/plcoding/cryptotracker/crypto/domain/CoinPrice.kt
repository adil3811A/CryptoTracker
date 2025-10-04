package com.plcoding.cryptotracker.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val priceUSD: Double,
    val deteTime: ZonedDateTime
)
