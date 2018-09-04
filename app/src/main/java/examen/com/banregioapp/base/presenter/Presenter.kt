package examen.com.banregioapp.base.presenter

import examen.com.banregioapp.base.view.MyView

open class Presenter<V : MyView> {
    private lateinit var view: V

    /*
    * Method to bind view
    */
    public fun bindView(view: V) {
        this.view = view
    }

    /**
     * Method to get an instance of view
     */
    fun getView(): V {
        return view
    }
}
