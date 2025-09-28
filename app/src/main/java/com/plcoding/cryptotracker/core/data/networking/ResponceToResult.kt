package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.core.domain.utils.NetworkError
import com.plcoding.cryptotracker.core.domain.utils.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


suspend inline fun <reified T> responceToResult(
    httpsResponse: HttpResponse
): Result<T, NetworkError> {
    return when (httpsResponse.status.value) {
        in 200..299 -> {
            try {
                Result.Success(httpsResponse.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }

        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        439 -> Result.Error(NetworkError.TOO_MANY_REQUEST)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}