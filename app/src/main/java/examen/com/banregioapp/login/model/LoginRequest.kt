package examen.com.banregioapp.login.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import examen.com.banregioapp.BR

data class LoginRequest(
        var _user: String = "",
        var _password: String = ""
) : BaseObservable() {

    var user: String
        @Bindable get() = _user
        set(value) {
            _user = value
            notifyPropertyChanged(BR.user)
        }

    var password: String
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }
}