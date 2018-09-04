package examen.com.banregioapp.login.view

import examen.com.banregioapp.base.view.MyView
import examen.com.corebanregio.model.LoginResponse

interface LoginView : MyView {
    abstract fun onCorrectLogin(loginResponse: LoginResponse)
}