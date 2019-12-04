package com.andreykapustindev.passwordwallet.view.views

import com.andreykapustindev.passwordwallet.utils.AppPrefManager

interface CreateAccountView {

    fun getPrefsManager(): AppPrefManager
    fun onCreateAccountClick()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}