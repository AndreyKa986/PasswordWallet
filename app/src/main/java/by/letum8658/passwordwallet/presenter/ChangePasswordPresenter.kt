package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.utils.encode

class ChangePasswordPresenter {

    private var view: ChangePasswordView? = null

    fun setView(view: ChangePasswordView?) {
        this.view = view
    }

    fun showItem(name: String) {
        view?.setName(name)
    }

    fun saveItem() {
        val password = view?.getPassword()!!
        val confirmP = view?.getConfirmPassword()
        if (password == confirmP) {
            val account = ItemManager.getName()!!
            val itemName = view?.getName()!!
            val cryptPassword = encode(password)
            ItemManager.updateItem(account, itemName, Item(cryptPassword), object : Callback() {
                override fun returnResult(text: String?) {
                    val list = arrayListOf(itemName, password)
                    view?.saveChange(list)
                }
            })
        } else {
            TODO() // дописать - пароли не совподают
        }
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}