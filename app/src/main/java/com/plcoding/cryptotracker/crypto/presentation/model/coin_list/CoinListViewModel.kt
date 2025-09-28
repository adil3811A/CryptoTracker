package com.plcoding.cryptotracker.crypto.presentation.model.coin_list

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