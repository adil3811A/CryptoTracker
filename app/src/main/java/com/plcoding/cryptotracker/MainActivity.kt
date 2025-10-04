package com.plcoding.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.*
import com.plcoding.cryptotracker.core.presentation.utils.ObserveAsEvent
import com.plcoding.cryptotracker.core.presentation.utils.toString
import com.plcoding.cryptotracker.crypto.presentation.model.coin_detail.CoinDetailScreen
import com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListEvents
import com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListScreen
import com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListViewModel
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state = viewModel.state.collectAsStateWithLifecycle()
                    ObserveAsEvent(viewModel.event) {events ->
                        when(events){
                            is CoinListEvents.Erorr -> {
                                Toast.makeText(applicationContext, events.message.toString(
                                    applicationContext
                                ), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    when{
                        state.value.selectedCoin!=null-> CoinDetailScreen(state.value, Modifier.padding(innerPadding))
                        else->CoinListScreen(
                            state = state.value,
                            onClick = viewModel::onAction,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerTheme {
        Greeting("Android")
    }
}