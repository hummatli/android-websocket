package com.challenge.app.repository.websocket

import com.challenge.app.data.Data
import kotlinx.coroutines.flow.StateFlow

interface StockRepository {
    val flow: StateFlow<Response<Map<String, String>>?>
    val data: Data
    fun stop()
    fun start()
}

sealed class Response<T> {
    data class Data<T>(val result: T) : Response<T>()
    data class Error<T>(val exception: Throwable) : Response<T>()
}