package examen.com.banregioapp.main.presenter

import android.content.Context
import android.util.Log
import examen.com.banregioapp.R
import examen.com.banregioapp.base.presenter.Presenter
import examen.com.banregioapp.main.view.MainView
import examen.com.banregioapp.utils.Network
import examen.com.corebanregio.data.RestClient
import examen.com.corebanregio.model.BranchResponse
import examen.com.corebanregio.utils.Constants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(val context: Context) : Presenter<MainView>() {
    companion object {
        private val TAG = MainPresenter::class.java.simpleName
    }

    fun getBranchs() {
        if (Network.isNetworkConnected(context)) {
            getView().showLoadingView()
            val service = RestClient.getClient(Constants.HOST)
            var responseCall = service.getBranchs()
            responseCall.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            object : Observer<ArrayList<BranchResponse>> {
                                override fun onComplete() {
                                }

                                override fun onSubscribe(d: Disposable) {
                                }

                                override fun onNext(t: ArrayList<BranchResponse>) {
                                    getView().hideLoadingView()
                                    getView().addMapFragment(t)
                                }

                                override fun onError(e: Throwable) {
                                    Log.d(MainPresenter.TAG, "onError()")
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