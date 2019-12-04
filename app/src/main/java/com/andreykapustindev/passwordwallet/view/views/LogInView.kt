package com.andreykapustindev.passwordwallet.view.views

import com.andreykapustindev.passwordwallet.utils.AppPrefManager

interface LogInView {

    fun getPrefsManager(): AppPrefManager
    fun setName(name: String)
    fun onLogInClick()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}