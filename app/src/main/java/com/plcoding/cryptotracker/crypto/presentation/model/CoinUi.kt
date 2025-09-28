package com.plcoding.cryptotracker.crypto.presentation.model

import androidx.annotation.DrawableRes
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.core.presentation.utils.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale

data class CoinUi(

    val id:String,
    val rank:Int,
    val name:String,
    val symbol:String,
    val marketCapUSD: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconREs:Int
)
data class DisplayableNumber(
    val value: Double,
    val formatted:String
)

fun Coin.toCoinUi(): CoinUi{
    return CoinUi(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUSD = marketCapUSD.toDsiplyableNumber(),
        priceUsd = priceUSD.toDsiplyableNumber(),
        changePercent24Hr = changePercent24Hr.toDsiplyableNumber(),
        iconREs = getDrawableIdForCoin(symbol)

    )
}

fun Double.toDsiplyableNumber() : DisplayableNumber{
    val foramtter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumIntegerDigits = 2
        maximumIntegerDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = foramtter.format(this)
    )
}