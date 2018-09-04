package examen.com.banregioapp.base.activity

import android.app.Dialog
import android.content.pm.PackageManager
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import examen.com.banregioapp.R
import examen.com.banregioapp.base.view.MyView
import examen.com.banregioapp.utils.ViewUtils


open class BaseActivity : AppCompatActivity(), MyView {
    private var progressDialog: Dialog? = null
    val REQUEST_FINE_LOCATION = 100
    var coordinatorLayout: CoordinatorLayout? = null


    override fun showErrorMessage(error: Error, title: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToast(msg: String) {
        coordinatorLayout?.let {
            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showLoadingView() {
        if (progressDialog == null)
            progressDialog = ViewUtils.showLoadingDialog(this)
        else
            progressDialog?.show()
    }

    override fun hideLoadingView() {
        progressDialog?.let {
            if (it.isShowing())
                it.cancel()
        }
    }

    open fun onBindingView() {}

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_FINE_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //Log.d("masterkey", "masterkey")
                    //showToast()

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //Log.d("masterkey", "masterkey")
                    showToast(getString(R.string.label_permission_cancel_gpg))
                }
                return
            }
        }
    }
}