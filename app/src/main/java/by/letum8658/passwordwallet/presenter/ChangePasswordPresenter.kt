package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager

class ChangePasswordPresenter {

    private var view: ChangePasswordView? = null

    fun setView(view: ChangePasswordView?) {
        this.view = view
    }

    fun showItem(id: Int) {
        val item = ItemManager.getItemById(id)
        view?.setName(item!!.name)
    }

    fun saveItem(id: Int) {
        val password = view?.getPassword()
        val confirmP = view?.getConfirmPassword()
        if (password == confirmP) {
            val name = view?.getName()
            ItemManager.updateItem(Item(name!!, password!!, id))
            view?.saveChange()
        } else view?.showMessage()
    }
}