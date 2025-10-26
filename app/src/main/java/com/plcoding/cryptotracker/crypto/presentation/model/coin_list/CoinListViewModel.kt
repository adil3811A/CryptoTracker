package com.plcoding.cryptotracker.crypto.presentation.model.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.utils.onError
import com.plcoding.cryptotracker.core.domain.utils.onSuccess
import com.plcoding.cryptotracker.crypto.domain.DataSource
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUi
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModel(
    private val dataSource: DataSource
) : ViewModel() {
    private val _state =MutableStateFlow(CoinListState())

    val state = _state.onStart {
        loadCoin()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinListState()
    )
    private val _event = Channel<CoinListEvents>()

    val event  =_event.receiveAsFlow()
    fun onAction(coinListAction: CoinListAction){
        when(coinListAction){
            is CoinListAction.OnCoinClick -> {
                selectCoin(coinListAction.coinUi)
            }
        }
    }

    // New public helper to select a coin by its id. It will try to find the coin in current state,
    // otherwise it will load the coin list and then select it if found.
    fun selectCoinById(coinId: String){
        val current = _state.value
        val found = current.coins.find { it.id == coinId }
        if(found!=null){
            selectCoin(found)
            return
        }
        // Not found - attempt to load coins then select
        viewModelScope.launch {
            _state.update { it.copy(IsLoading = true) }
            dataSource.getCoin()
                .onSuccess { data->
                    val uiList = data.map { coin -> coin.toCoinUi() }
                    _state.update { it.copy(IsLoading = false, coins = uiList) }
                    val f = uiList.find { it.id == coinId }
                    if(f!=null){
                        selectCoin(f)
                    }
                }
                .onError { error ->
                    _state.update { it.copy(IsLoading = false) }
                    _event.send(CoinListEvents.Erorr(error))
                }
        }
    }

    private fun selectCoin(coinUi: CoinUi){
        _state.update {
            it.copy(selectedCoin = coinUi)
        }
        viewModelScope.launch {
            dataSource.getCoinHistory(
                coinID = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            )
                .onSuccess {histor->
                    Log.d("Adil", "History is $histor")
                }
                .onError {error ->
                    Log.d("Adil", "error is $error")
                    _state.update { it.copy(IsLoading = false) }
                    _event.send(CoinListEvents.Erorr(error))

                }
        }
    }
    private fun loadCoin(){
        viewModelScope.launch {
            _state.update { it.copy(
                IsLoading = true
            ) }
            dataSource.getCoin()
                .onSuccess { data->
                    _state.update { it.copy(
                        IsLoading = false,
                        coins = data.map { coin ->  coin.toCoinUi() }
                    ) }
            }
                .onError {error->
                    _state.update { it.copy(IsLoading = false) }
                    _event.send(CoinListEvents.Erorr(error))
                }
        }
    }
}