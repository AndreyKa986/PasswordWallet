package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.AppPrefManager

interface LogInView {

    fun getPrefsManager(): AppPrefManager
    fun setName(name: String)
    fun getName(): String
    fun getPassword(): String
    fun logIn()
    fun create()
    fun showMessage(number: Int)
}