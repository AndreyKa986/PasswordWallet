package by.letum8658.passwordwallet.view.views

import by.letum8658.passwordwallet.utils.AppPrefManager

interface CreateAccountView {

    fun getPrefsManager(): AppPrefManager
    fun onCreateAccountClick()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}