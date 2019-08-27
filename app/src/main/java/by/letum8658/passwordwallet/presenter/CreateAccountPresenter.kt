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
                            view?.showMessage(1)
                        }
                    }
                })
            } else {
                view?.showMessage(2)
            }
        } else {
            view?.showMessage(3)
        }
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}