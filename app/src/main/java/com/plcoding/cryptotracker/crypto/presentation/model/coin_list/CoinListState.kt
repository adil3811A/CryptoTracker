package com.plcoding.cryptotracker.crypto.presentation.model.coin_list

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.crypto.domain.Coin

@Immutable
data class CoinListState(
    val IsLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val selectedCoin: Coin? = null
)