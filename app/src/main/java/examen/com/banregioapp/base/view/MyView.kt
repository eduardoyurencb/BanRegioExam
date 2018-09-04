package examen.com.banregioapp.base.view

interface MyView {
    /*
    * Method to show an error into ViewUtils implementation
    * @param Error error
    * */
    abstract fun showErrorMessage(error: Error, title: String)

    abstract fun showToast(msg: String)

    fun showLoadingView()
    fun hideLoadingView()
}