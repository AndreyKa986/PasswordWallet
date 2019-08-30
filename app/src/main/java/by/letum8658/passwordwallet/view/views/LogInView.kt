package by.letum8658.passwordwallet.view.views

import by.letum8658.passwordwallet.utils.AppPrefManager

interface LogInView {

    fun getPrefsManager(): AppPrefManager
    fun setName(name: String)
    fun onLogInClick()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}