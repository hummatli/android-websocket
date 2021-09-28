package com.challenge.app

import android.app.Application
import com.challenge.app.di.repositoryModule
import com.challenge.app.di.viewModelsModule
import com.challenge.app.utils.TimberInitializer
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    private val timberInitializer by inject<TimberInitializer>()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)

            androidFileProperties()

            modules(listOf(repositoryModule, viewModelsModule))
        }

        timberInitializer.init(this)
    }
}
