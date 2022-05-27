package eg.com.invive.barberia_ktx.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.yasser.bosta_task.Utils.Interfaces.NetworkStatus


class NetworkReceiver(private val networkStatus: NetworkStatus):BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {
       if (intent?.action?.equals("android.net.conn.CONNECTIVITY_CHANGE",true)!!){

           when(checkConnection(context)){

               true -> networkStatus.connect()
               false-> networkStatus.notConnect()

           }


       }

    }



    private fun checkConnection (context: Context?):Boolean {

        var result = false
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result

    }


}