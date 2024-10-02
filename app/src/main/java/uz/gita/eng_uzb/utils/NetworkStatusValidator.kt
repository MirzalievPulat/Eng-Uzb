package uz.gita.eng_uzb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import uz.gita.eng_uzb.app.App


class NetworkStatusValidator private constructor() {
    companion object{
        private var instance:NetworkStatusValidator? = null

        fun getInstance():NetworkStatusValidator{
            if (instance == null){
                instance = NetworkStatusValidator()
            }
            return instance!!
        }
    }

    var isNetworkEnabled: Boolean = false
        private set

    fun listenNetworkStatus(
        onAvailable: () -> Unit,
        onLost: () -> Unit
    ) {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isNetworkEnabled = true
                onAvailable.invoke()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isNetworkEnabled = false
                onLost.invoke()
            }
        }

        val connectivityManager = App.context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}