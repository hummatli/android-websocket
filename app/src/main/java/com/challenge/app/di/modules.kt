package com.challenge.app.di

import com.challenge.app.data.Data
import com.challenge.app.data.DataImpl
import com.challenge.app.flow.main.MainPageViewModel
import com.challenge.app.repository.websocket.StockRepository
import com.challenge.app.repository.websocket.StockRepositoryImpl
import com.challenge.app.utils.TimberInitializer
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

//////////////////////////////////// Repo module //////////////////////////
val repositoryModule: Module = module {

    //Network
    single {
        Json {
            isLenient = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    single<Data> {
        DataImpl()
    }

    single<StockRepository> {
        StockRepositoryImpl(
            json = get(),
            data = get()
        )
    }

    //Initializers
    single { TimberInitializer() }

}

//////////////////////////////////// View Models ////////////////////////////////////
val viewModelsModule = module {
    viewModel {
        MainPageViewModel(stockRepository = get())
    }
}