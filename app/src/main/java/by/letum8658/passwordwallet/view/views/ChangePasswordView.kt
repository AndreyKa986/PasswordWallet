package by.letum8658.passwordwallet.view.views

interface ChangePasswordView {

    fun setName(name: String)
    fun getName(): String
    fun getPassword(): String
    fun getConfirmPassword(): String
    fun saveChange(list: ArrayList<String>)
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}