package examen.com.banregioapp.login.presenter

import android.content.Context
import android.util.Log
import examen.com.banregioapp.R

import examen.com.banregioapp.base.presenter.Presenter
import examen.com.banregioapp.login.model.LoginRequest
import examen.com.banregioapp.login.view.LoginView
import examen.com.banregioapp.utils.Network
import examen.com.corebanregio.data.RestClient
import examen.com.corebanregio.model.LoginResponse
import examen.com.corebanregio.utils.Constants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(val context: Context) : Presenter<LoginView>() {
    private var CORRECT_USER = "Alex"
    private var CORRECT_PASSWORD = "iosfan"
    private var MSG_USER = "El usuario es incorrecto"
    private var MSG_PASSWORD = "La contraseña es incorrecta"

    companion object {
        private val TAG = LoginPresenter::class.java.simpleName
    }

    fun getLogin(loginRequest: LoginRequest) {
        var flag = true
        if (loginRequest._user != CORRECT_USER) {
            flag = false
            getView().showToast(MSG_USER)
        } else if (loginRequest._password != CORRECT_PASSWORD) {
            flag = false
            getView().showToast(MSG_PASSWORD)
        }

        if(flag) {
            if (Network.isNetworkConnected(context)) {
                getView().showLoadingView()
                val service = RestClient.getClient(Constants.HOST)
                val responseCall = service.getLoginResponse()
                responseCall.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                object : Observer<LoginResponse> {
                                    override fun onComplete() {
                                    }

                                    override fun onSubscribe(d: Disposable) {
                                    }

                                    override fun onNext(t: LoginResponse) {
                                        Log.d(TAG, "onNext()")
                                        getView().hideLoadingView()
                                        getView().onCorrectLogin(t)
                                    }

                                    override fun onError(e: Throwable) {
                                        Log.d(TAG, "onError()")
                                        getView().hideLoadingView()
                                        getView().showToast(context.getString(R.string.label_error))
                                    }
                                }
                        )
            } else {
                getView().showToast(context.getString(R.string.label_error_network))
            }
        }
    }
}