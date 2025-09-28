package com.plcoding.cryptotracker.di

import com.plcoding.cryptotracker.core.data.networking.HtpClintFactory
import com.plcoding.cryptotracker.crypto.data.RemoteCoinDataSource
import com.plcoding.cryptotracker.crypto.domain.DataSource
import com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single { HtpClintFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<DataSource>()

    viewModelOf(::CoinListViewModel)

}