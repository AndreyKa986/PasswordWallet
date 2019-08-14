package by.letum8658.passwordwallet.presenter

interface CreateAccountView {

    fun getName(): String
    fun getPassword(): String
    fun getConfirmPassword(): String
    fun createAccount(userName: String, password: String)
    fun showMessage()
}