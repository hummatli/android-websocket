package com.challenge.app.data

import com.challenge.app.models.Stock

interface Data {
    val stocks: List<Stock>
}