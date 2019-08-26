package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.User

class CreateAccountPresenter {

    private var view: CreateAccountView? = null

    fun setView(view: CreateAccountView?) {
        this.view = view
    }

    fun createAccount() {
        val name = view?.getName()
        val password = view?.getPassword()!!
        val confirmPassword = view?.getConfirmPassword()
        if (name!!.isNotBlank()) {
            if (password == confirmPassword) {
                ItemManager.getAccount(name, object : Callback() {
                    override fun returnResult(text: String?) {
                        if (text == null) {
                            ItemManager.createAccount(name, User(password), object : Callback() {
                                override fun returnResult(text: String?) {
                                    ItemManager.setName(name)
                                    view?.createAccount()
                                }
                            })
                        } else {
                            TODO() // дописать - такой пользаватель существует
                        }
                    }
                })
            } else {
                TODO() // дописать - пароли не совподают
                // view?.showMessage()
            }
        } else {
            TODO() // дописать - введите имя пользователя
        }
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}