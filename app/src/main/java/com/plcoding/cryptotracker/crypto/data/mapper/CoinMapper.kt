package com.plcoding.cryptotracker.crypto.data.mapper

import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.dto.CoinPriceDTO
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId


fun CoinDto.toCoin(): Coin{
    return Coin(
        id = id,
        rank = rank.toInt(),
        name = name,
        symbol = symbol,
        marketCapUSD = marketCapUsd.toDouble(),
        priceUSD = priceUsd.toDouble(),
        changePercent24Hr = changePercent24Hr.toDouble()
    )
}

fun CoinPriceDTO.toCoinPrice(): CoinPrice{
    return CoinPrice(
        priceUSD = priceUsd,
        deteTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC"))
    )
}