package com.andreykapustindev.passwordwallet.view.views

interface InformationView {

    fun setLogin(name: String)
    fun setPassword(password: String)
    fun delete()
    fun change()
    fun progressBarOn()
    fun progressBarOff()
    fun showMessage()
}