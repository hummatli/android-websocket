package com.challenge.app.repository.websocket

import com.challenge.app.BuildConfig
import com.challenge.app.data.Data
import com.challenge.app.models.remote.StockResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import timber.log.Timber

class StockRepositoryImpl(
    private val json: Json,
    override val data: Data
) : StockRepository {

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

    private lateinit var job: Job
    private lateinit var ws: WebSocket

    private val currentStocks = mutableMapOf<String, String>()
    private var lastSentDate: Long = 0

    private val _flow = MutableStateFlow<Response<Map<String, String>>?>(null)
    override val flow: StateFlow<Response<Map<String, String>>?>
        get() = _flow

    override fun start() {
        lastSentDate = System.currentTimeMillis()

        job = Job()
        val coroutineScope = CoroutineScope(job + Dispatchers.Default)

        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BuildConfig.BASE_API_URL)
                .build()


            val listener = StockWebSocketListener(
                onOpen = { _ ->
                    data.stocks.forEach {
                        ws.send("""{"subscribe":"${it.isin}"}""")
                    }
                },
                onClosing = { _, _ ->
                    ws.close(NORMAL_CLOSURE_STATUS, null)
                },
                onMessage = { text ->
                    coroutineScope.launch {
                        json.decodeFromString<StockResponse>(text).run {
                            currentStocks.put(isin, priceFormatted)
                        }

                        System.currentTimeMillis().run {
                            //Only send after 1500 ms
                            if (this - lastSentDate > 1500) {
                                lastSentDate = this

                                //Send updated stock map to the flow
                                _flow.emit(Response.Data(HashMap(currentStocks)))
                            }
                        }
                    }
                },
                onFailure = { t, _ ->
                    coroutineScope.launch {
                        _flow.emit(Response.Error(exception = t))
                    }
                }
            )

            ws = client.newWebSocket(request, listener)

            client.dispatcher.executorService.shutdown()

        } catch (ex: Exception) {
            coroutineScope.launch { _flow.emit(Response.Error(ex)) }
            return
        }
    }

    override fun stop() {
        try {
            data.stocks.forEach {
                ws.send("""{"unsubscribe":"${it.isin}"}""")
            }
            ws.close(NORMAL_CLOSURE_STATUS, "{\"Goodbye\"}")
            job.cancel()
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}

