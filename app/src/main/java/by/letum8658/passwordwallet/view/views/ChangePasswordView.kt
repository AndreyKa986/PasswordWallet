package by.letum8658.passwordwallet.view.views

interface ChangePasswordView {

    fun setName(name: String)
    fun onSaveClick(list: ArrayList<String>)
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}