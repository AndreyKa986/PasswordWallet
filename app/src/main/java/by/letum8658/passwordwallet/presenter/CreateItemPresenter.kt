package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager

class CreateItemPresenter {

    private var view: CreateItemView? = null

    fun setView(view: CreateItemView?) {
        this.view = view
    }

    fun setData(list: ArrayList<String>?) {
        if (list != null) {
            if (list[0].isNotBlank()) {
                view?.setName(list[0])
            }
            if (list[1].isNotBlank()) {
                view?.setPassword(list[1])
                view?.setConfirmPassword(list[1])
            }
        }
    }

    fun createPassword() {
        val name = view?.getName()
        view?.createPassword(name!!)
    }

    fun saveItem() {
        val name = view?.getName()
        val password = view?.getPassword()
        val confirm = view?.getConfirmPassword()
        if (password == confirm) {
            val id = System.currentTimeMillis().toInt()
            ItemManager.addNewItem(Item(name!!, password!!, id))
            view?.saveItem()
        } else view?.showMessage()
    }
}