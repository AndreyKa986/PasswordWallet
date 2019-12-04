package com.andreykapustindev.passwordwallet.view.views

interface ChangePasswordView {

    fun onSaveClick(list: ArrayList<String>)
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}