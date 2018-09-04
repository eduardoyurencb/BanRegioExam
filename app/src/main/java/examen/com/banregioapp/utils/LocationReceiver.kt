package examen.com.banregioapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.LocationListener
import examen.com.banregioapp.base.activity.BaseActivity


class LocationReceiver(
        var activity: BaseActivity,
        var locationCallbacks: LocationCallbacks
) : LocationListener {
    private var manager: LocationManager? = null
    private val TAG = "LocationReceiver"
    private val LOCATION_INTERVAL = 1000
    private val LOCATION_DISTANCE = 10f


    fun startLocation() {
        if (checkPermissions()) {
            manager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val managerAux = manager
            if (managerAux?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
                locationCallbacks.onUnavailable()
            } else {
                locationCallbacks.connecting()
                startLocalConnection()
            }
        }
    }

    private fun startLocalConnection() {
        try {
            manager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                    mLocationListeners[1])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "network provider does not exist, " + ex.message)
        }

        try {
            manager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                    mLocationListeners[0])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "gps provider does not exist " + ex.message)
        }

    }

    override fun onLocationChanged(p0: Location?) {}

    interface LocationCallbacks {
        fun missingPermissions()
        fun onConnect()
        fun connecting()
        fun onUnavailable()
        fun onGetLocation(location: Location)
    }

    private inner class LocationListener(provider: String) : android.location.LocationListener {
        internal var mLastLocation: Location

        init {
            Log.e(TAG, "LocationListener $provider")
            mLastLocation = Location(provider)
        }

        override fun onLocationChanged(location: Location?) {
            removeUpdatesManager()
            if (location != null)
                locationCallbacks.onGetLocation(location)
            else {
                locationCallbacks.onUnavailable()
            }
        }

        override fun onProviderDisabled(provider: String) {
            locationCallbacks.onUnavailable()
            removeUpdatesManager()
        }

        override fun onProviderEnabled(provider: String) {
            Log.e(TAG, "onProviderEnabled: $provider")
            locationCallbacks.onUnavailable()
            removeUpdatesManager()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

    private fun removeUpdatesManager() {
        manager?.removeUpdates(mLocationListeners[0])
        manager?.removeUpdates(mLocationListeners[1])
    }

    private var mLocationListeners = arrayOf(LocationListener(LocationManager.GPS_PROVIDER), LocationListener(LocationManager.NETWORK_PROVIDER))

    /*
    * Solicitud de permisos
    * */

    fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                        activity as Context,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                activity.REQUEST_FINE_LOCATION)
    }
}