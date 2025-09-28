package com.plcoding.cryptotracker.crypto.data.mapper

import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.domain.Coin


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