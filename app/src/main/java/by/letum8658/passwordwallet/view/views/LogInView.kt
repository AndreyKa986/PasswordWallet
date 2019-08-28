package by.letum8658.passwordwallet.view.views

import by.letum8658.passwordwallet.model.AppPrefManager

interface LogInView {

    fun getPrefsManager(): AppPrefManager
    fun setName(name: String)
    fun getName(): String
    fun getPassword(): String
    fun logIn()
    fun create()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}