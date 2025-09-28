package com.plcoding.cryptotracker.crypto.presentation.model.coin_list

import com.plcoding.cryptotracker.core.domain.utils.NetworkError

sealed interface CoinListEvents {
    data class Erorr(val message: NetworkError): CoinListEvents
}