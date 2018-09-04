package examen.com.corebanregio.data

import examen.com.corebanregio.model.BranchResponse
import examen.com.corebanregio.model.LoginResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterfaces {
    @GET("login/")
    abstract fun getLoginResponse(): Observable<LoginResponse>

    @GET("sucursales/")
    abstract fun getBranchs(): Observable<ArrayList<BranchResponse>>
}