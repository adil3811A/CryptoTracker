package com.plcoding.cryptotracker.crypto.data

import com.plcoding.cryptotracker.core.data.networking.constructUrl
import com.plcoding.cryptotracker.core.data.networking.safeCall
import com.plcoding.cryptotracker.core.domain.utils.NetworkError
import com.plcoding.cryptotracker.core.domain.utils.Result
import com.plcoding.cryptotracker.core.domain.utils.map
import com.plcoding.cryptotracker.crypto.data.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.data.dto.CoinResponseDto
import com.plcoding.cryptotracker.crypto.data.mapper.toCoin
import com.plcoding.cryptotracker.crypto.data.mapper.toCoinPrice
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import com.plcoding.cryptotracker.crypto.domain.DataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import java.time.ZoneId
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
        val startMilles = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMilles = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinID/history")
            ){
                parameter("interval","h6")
                parameter("start",startMilles)
                parameter("end",endMilles)
            }
        }.map {response->
            response.data.map {data-> data.toCoinPrice() }
        }
    }
}