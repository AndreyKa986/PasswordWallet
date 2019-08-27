package by.letum8658.passwordwallet.presenter

interface CreateAccountView {

    fun getName(): String
    fun getPassword(): String
    fun getConfirmPassword(): String
    fun createAccount()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}