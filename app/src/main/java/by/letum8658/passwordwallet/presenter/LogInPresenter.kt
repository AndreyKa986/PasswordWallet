package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.AppPrefManager

class LogInPresenter {

    private var view: LogInView? = null
    private lateinit var prefsManager: AppPrefManager

    fun setView(view: LogInView?) {
        this.view = view
        prefsManager = view?.getPrefsManager()!!
    }

    fun setName() {
        val name = prefsManager.getName()
        view?.setName(name)
    }

    fun saveName() {
        val name = view?.getName()
        prefsManager.saveName(name!!)
    }

    fun logIn() {
        val name = view?.getName()
        val password = view?.getPassword()
        view?.logIn(name!!, password!!)
    }

    fun create() {
        view?.create()
    }
}