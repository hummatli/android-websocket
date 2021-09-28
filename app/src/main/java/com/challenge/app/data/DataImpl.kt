package com.challenge.app.data

import com.challenge.app.models.Stock

class DataImpl : Data {
    override val stocks = listOf(
        Stock(
            name = "Alphabet Inc",
            isin = "US02079K1079"
        ),
        Stock(
            name = "Tesla Motors",
            isin = "US88160R1014"
        ),
        Stock(
            name = "Coinbase",
            isin = "US19260Q1076"
        ),
        Stock(
            name = "Apple",
            isin = "US0378331005"
        ),
        Stock(
            name = "Netflix",
            isin = "US64110L1061"
        ),
        Stock(
            name = "Virgin Galactic",
            isin = "US92766K1060"
        ),
        Stock(
            name = "Twitter",
            isin = "US90184L1026"
        ),
        Stock(
            name = "Amazon",
            isin = "US0231351067"
        ),
        Stock(
            name = "PayPal",
            isin = "US70450Y1038"
        ),
        Stock(
            name = "Microsoft",
            isin = "US5949181045"
        ),
    )
}