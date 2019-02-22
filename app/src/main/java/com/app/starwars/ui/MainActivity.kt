package com.app.starwars.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.starwars.StarWarsApplication
import com.app.starwars.model.system.ConnectivityReceiver
import com.app.starwars.ui.global.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    lateinit var navController: NavController
    lateinit var connectivityReceiver: ConnectivityReceiver

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.starwars.R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = findNavController(com.app.starwars.R.id.my_nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar, navController)

        connectivityReceiver = ConnectivityReceiver()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, intentFilter)
        StarWarsApplication.setConnectivityListener(this)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
        StarWarsApplication.setConnectivityListener(null)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        errorWidget.showContainer(!isConnected)
    }

    override fun showProgress(progress: Boolean) {

    }

    override fun showError(error: Throwable) {
        super.showError(error)
    }
}