package examen.com.banregioapp.main.view

import examen.com.banregioapp.base.view.MyView
import examen.com.corebanregio.model.BranchResponse

interface MainView: MyView {
    fun addMapFragment(t: ArrayList<BranchResponse>)
}