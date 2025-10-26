package com.plcoding.cryptotracker.crypto.presentation.model.coin_detail.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import com.plcoding.cryptotracker.crypto.presentation.model.coin_detail.ChartStyle
import com.plcoding.cryptotracker.crypto.presentation.model.coin_detail.DataPoint

@Composable
fun LineCart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint:(DataPoint)-> Unit ={},
    onXLabelWidthChange:(Float)-> Unit,
    showHelperLine : Boolean = false
) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )

    val visibleDataPoint = remember(dataPoints,visibleDataPointIndices) {
        dataPoints.slice(visibleDataPointIndices)
    }
    val maxYValue = remember(visibleDataPoint) {
        visibleDataPoint.maxOfOrNull {it.Y } ?: 0f
    }
    val minYValue = remember(visibleDataPoint) {
        visibleDataPoint.minByOrNull {it.Y } ?: 0f
    }
    val measurer = rememberTextMeasurer()




}


