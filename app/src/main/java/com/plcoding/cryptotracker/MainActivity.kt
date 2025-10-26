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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

                    // Navigation setup
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.CoinList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.CoinList.route) {
                            CoinListScreen(
                                state = state.value,
                                onClick = { action ->
                                    // Keep existing VM behavior
                                    viewModel.onAction(action)
                                    // If action is a coin click, navigate to details
                                    when(action) {
                                        is com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListAction.OnCoinClick -> {
                                            navController.navigate("${Routes.CoinDetail.route}/${action.coinUi.id}")
                                        }
                                    }
                                },
                                modifier = Modifier
                            )
                        }

                        composable(
                            route = "${Routes.CoinDetail.route}/{coinId}",
                            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            // Ensure ViewModel has selected coin for this id
                            val coinId = backStackEntry.arguments?.getString("coinId")
                            if(coinId!=null){
                                // ask viewModel to select coin if needed
                                viewModel.selectCoinById(coinId)
                            }
                            // We rely on ViewModel state for the selected coin. If user navigated directly
                            // without selecting from list, selectedCoin might be null. For now show the
                            // detail screen based on state value (it will show loader / empty if null).
                            CoinDetailScreen(state = state.value, modifier = Modifier)
                        }
                    }
                }
            }
        }
    }
}

// Simple navigation routes
private sealed class Routes(val route: String) {
    object CoinList : Routes("coin_list")
    object CoinDetail : Routes("coin_detail")
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