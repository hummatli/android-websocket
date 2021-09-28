package com.challenge.app.flow.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.app.base.BaseViewModel
import com.challenge.app.extensions.forceRefresh
import com.challenge.app.models.Stock
import com.challenge.app.repository.websocket.Response
import com.challenge.app.repository.websocket.StockRepository
import com.challenge.app.utils.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainPageViewModel(
    private val stockRepository: StockRepository
) : BaseViewModel() {

    private val stocks: List<Stock>
        get() = stockRepository.data.stocks

    private val _listStateLiveData = MutableLiveData(stocks)
    val listStateLiveData: LiveData<List<Stock>> = _listStateLiveData

    private val _effect = SingleLiveEvent<Effect>()
    val effect: SingleLiveEvent<Effect> = _effect


    //Starts repository to receive input from WS
    fun start() {
        stockRepository.start()
        viewModelScope.launch {
            stockRepository.flow.collect { response ->
                when(response) {
                    is Response.Data -> {
                        response.result.let { map->
                            _listStateLiveData.value?.forEach {
                                it.price = map[it.isin]
                            }
                            _listStateLiveData.forceRefresh()
                        }
                    }
                    is Response.Error -> {
                        _effect.value = Effect.ShowErrorMessage(response.exception)
                    }
                }
            }
        }
    }

    //Stops repository to receive input from WS
    fun stop() {
        stockRepository.stop()
    }
}

sealed class Effect {
    data class ShowErrorMessage(val ex: Throwable) : Effect()
}
