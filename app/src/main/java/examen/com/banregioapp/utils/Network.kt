package examen.com.banregioapp.utils

import android.content.Context
import android.net.ConnectivityManager

class Network {
    companion object {
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.getActiveNetworkInfo() != null
        }
    }
}