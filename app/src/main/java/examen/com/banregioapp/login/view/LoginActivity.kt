package examen.com.banregioapp.login.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import examen.com.banregioapp.R
import examen.com.banregioapp.base.activity.BaseActivity
import examen.com.banregioapp.databinding.ActivityLoginBinding
import examen.com.banregioapp.login.model.LoginRequest
import examen.com.banregioapp.login.presenter.LoginPresenter
import examen.com.corebanregio.model.LoginResponse
import android.content.Intent
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import examen.com.banregioapp.main.view.MainActivity
import examen.com.banregioapp.utils.LocationReceiver


class LoginActivity :
        BaseActivity(),
        LoginView,
        LoginIFace,
        LocationReceiver.LocationCallbacks {

    private var binding: ActivityLoginBinding? = null
    private var loginPresenter: LoginPresenter? = null
    private var loginRequest: LoginRequest? = null
    private var locationReceiver: LocationReceiver? = null
    private var location: LatLng? = null
    private val lat = 25.694733
    private val lng = -100.318455

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_login
        )
        onBindingView()
    }

    override fun onBindingView() {
        location = LatLng(lat, lng)
        coordinatorLayout = binding?.coordinatorLayout
        loginPresenter = LoginPresenter(this)
        loginPresenter?.bindView(this)
        binding?.loginIFace = this
        loginRequest = LoginRequest()
        binding?.loginRequest = loginRequest

        locationReceiver = LocationReceiver(
                this,
                this
        )
        locationReceiver?.startLocation()
    }

    override fun onClickLogin() {
        Log.d(TAG, "onClickLogin()")
        loginRequest?.let {
            loginPresenter?.getLogin(it)
        }
    }

    override fun onCorrectLogin(loginResponse: LoginResponse) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("location", location)
        startActivity(intent)
    }

    override fun missingPermissions() {
    }

    override fun onConnect() {
    }

    override fun connecting() {
        showLoadingView()
    }

    override fun onUnavailable() {
        hideLoadingView()
        showToast(getString(R.string.label_gps_error))
    }

    override fun onGetLocation(nLocation: Location) {
        hideLoadingView()
        showToast(getString(R.string.label_gps_success))
        location = LatLng(nLocation.latitude, nLocation.longitude)

    }
}