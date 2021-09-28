package com.challenge.app.repository.websocket

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber

class StockWebSocketListener(
    private val onOpen: ((response: Response) -> Unit)? = null,
    private val onMessage: ((text: String) -> Unit)? = null,
    private val onMessageBytes: ((bytes: ByteString) -> Unit)? = null,
    private val onClosing: ((code: Int, reason: String) -> Unit)? = null,
    private val onFailure: ((t: Throwable, response: Response?) -> Unit)? = null
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        onOpen?.invoke(response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        Timber.i("Websocket Receiving : $text")
        onMessage?.invoke(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Timber.i("Websocket Receiving bytes : " + bytes.hex())
        onMessageBytes?.invoke(bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Timber.i("Websocket Closing : $code / $reason")
        onClosing?.invoke(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Timber.i("Websocket Error : " + t.stackTrace)
        onFailure?.invoke(t, response)
    }
}