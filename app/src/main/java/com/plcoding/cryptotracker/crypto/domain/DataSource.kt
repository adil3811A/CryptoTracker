package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.cryptotracker.core.domain.utils.NetworkError
import com.plcoding.cryptotracker.core.domain.utils.Result

interface DataSource {
    suspend fun getCoin(): Result<List<Coin>, NetworkError>
}