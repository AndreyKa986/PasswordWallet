package by.letum8658.passwordwallet.presenter

interface ChangePasswordView {

    fun setName(name: String)
    fun getName(): String
    fun getPassword(): String
    fun getConfirmPassword(): String
    fun saveChange()
    fun showMessage()
}