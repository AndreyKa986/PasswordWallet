package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.AppPrefManager
import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.ItemManager

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
        val name = view?.getName()!!
        val password = view?.getPassword()!!
        if (name.isNotBlank()) {
            if (password.isNotBlank()) {
                ItemManager.getAccount(name, object : Callback() {
                    override fun returnResult(text: String?) {
                        if (text != null) {
                            if (password == text) {
                                ItemManager.setName(name)
                                if (ItemManager.getItemList().isNotEmpty()) {
                                    ItemManager.clearItemList()
                                }
                                view?.logIn()
                            } else {
                                TODO() // дописать - не правильно введен пароль
                            }
                        } else {
                            TODO() // дописать - нет такого пользователя
                        }
                    }
                })
            } else
                TODO() // дописать - введите пароль
        } else {
            TODO() // дописать - введите имя пользователя
        }
    }

    fun create() {
        view?.create()
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}