package com.challenge.app.models.remote

import kotlinx.serialization.Serializable
import java.text.DecimalFormat

@Serializable
data class StockResponse(
    val isin: String,
    val price: Double
) {
    val priceFormatted: String
        get() = DecimalFormat("#,###.00").format(price)
}
