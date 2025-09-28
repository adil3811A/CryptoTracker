package com.plcoding.cryptotracker.core.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


@Composable
fun <T>ObserveAsEvent(
    event: Flow<T>,
    key1:Any? = null,
    key2:Any? = null,
    onEvent:(T)-> Unit
) {
    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    LaunchedEffect(lifeCycleOwner.lifecycle, key1 , key2) {
        withContext(Dispatchers.Main.immediate){
            lifeCycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                event.collect(onEvent)
            }
        }
    }
}