package com.plcoding.cryptotracker.crypto.presentation.model.coin_list

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUi

@Immutable
data class CoinListState(
    val IsLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)