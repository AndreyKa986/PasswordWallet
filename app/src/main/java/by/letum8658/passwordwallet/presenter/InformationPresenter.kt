package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.utils.decode

class InformationPresenter {

    private var view: InformationView? = null

    fun setView(view: InformationView?) {
        this.view = view
    }

    fun showData(item: String) {
        if (item.isNotBlank()) {
            view?.setName(item)
            val name = ItemManager.getName()!!
            ItemManager.getItemPassword(name, item, object : Callback() {
                override fun returnResult(text: String?) {
                    val password = decode(text!!)
                    view?.setPassword(password)
                }
            })
        } else {
            val list = view?.getInformationList()!!
            view?.setName(list[0])
            view?.setPassword(list[1])
        }
    }

    fun delete() {
        view?.delete()
    }

    fun change() {
        view?.change()
    }

    fun ok() {
        view?.ok()
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}