package com.app.starwars

import android.app.Application
import android.content.Context
import com.app.starwars.model.system.ConnectivityReceiver

class StarWarsApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: StarWarsApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener?) {
            ConnectivityReceiver.connectivityReceiverListener = listener
        }
    }

    override fun onCreate() {
        super.onCreate()
    }


}