package com.plcoding.cryptotracker.crypto.data

import com.plcoding.cryptotracker.core.data.networking.constructUrl
import com.plcoding.cryptotracker.core.data.networking.safeCall
import com.plcoding.cryptotracker.core.domain.utils.NetworkError
import com.plcoding.cryptotracker.core.domain.utils.Result
import com.plcoding.cryptotracker.core.domain.utils.map
import com.plcoding.cryptotracker.crypto.data.dto.CoinResponseDto
import com.plcoding.cryptotracker.crypto.data.mapper.toCoin
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import com.plcoding.cryptotracker.crypto.domain.DataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : DataSource {
    override suspend fun getCoin(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto>{
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { responseDto -> responseDto.data.map { e->e.toCoin() } }
    }

    override suspend fun getCoinHistory(
        coinID: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {

    }
}