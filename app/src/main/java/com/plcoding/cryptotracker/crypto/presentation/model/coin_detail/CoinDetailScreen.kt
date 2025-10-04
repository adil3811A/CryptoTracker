package com.plcoding.cryptotracker.crypto.presentation.model.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.presentation.model.coin_detail.component.InfoCard
import com.plcoding.cryptotracker.crypto.presentation.model.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUi
import com.plcoding.cryptotracker.crypto.presentation.model.toDsiplyableNumber
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    val color= if(isSystemInDarkTheme()) {
        Color.White
    }else{
        Color.Black
    }
    if(state.IsLoading){
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else if(state.selectedCoin!=null){
        val coin = state.selectedCoin
        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconREs),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = color
            )
            Text(
                coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = color
            )
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(R.string.market_cap),
                    formaterText = "$ ${coin.marketCapUSD.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(R.string.price),
                    formaterText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )
                val absoluteFormatValue = (coin.priceUsd.value*(coin.changePercent24Hr.value/100)).toDsiplyableNumber()
                val ispositive = coin.changePercent24Hr.value>0.0
                val contentColor = if(ispositive){
                    if(isSystemInDarkTheme()) Color.Green else greenBackground
                }else{
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(R.string.change_last_24_hr),
                    formaterText = "$ ${coin.marketCapUSD.formatted}",
                    icon =if(ispositive){
                        ImageVector.vectorResource(R.drawable.trending)
                    }else{
                        ImageVector.vectorResource(R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }
        }
    }
}

@Preview
@Composable
fun previewForCoinDetail(modifier: Modifier = Modifier) {
    CryptoTrackerTheme {
        CoinDetailScreen(
            CoinListState().copy(
                selectedCoin = previewcoin
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

internal val previewcoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "BitCoin",
    symbol = "BTC",
    marketCapUSD = 123456789.56,
    priceUSD = 21341111.00,
    changePercent24Hr = -0.1
).toCoinUi()