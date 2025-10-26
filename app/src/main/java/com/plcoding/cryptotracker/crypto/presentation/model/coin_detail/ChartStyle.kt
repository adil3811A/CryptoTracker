package com.plcoding.cryptotracker.crypto.presentation.model.coin_detail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class ChartStyle(
    val chartLineColor: Color,
    val underSelectedColor: Color,
    val selectedColor: Color,
    val helperLineThicknessPx: Float,
    val axisLineThicknessSize: Float,
    val labelFontSize: TextUnit,
    val minSyllableSpacing: Dp,
    val verticalPadding:Dp,
    val horizontalPadding: Dp,
    val xAxisLabelSpacing: Dp,
)
