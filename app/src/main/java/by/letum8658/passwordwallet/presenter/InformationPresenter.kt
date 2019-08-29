package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.ItemManager

class InformationPresenter {

    private var view: InformationView? = null

    fun setView(view: InformationView?) {
        this.view = view
    }

    fun showData(id: Int) {
        val item = ItemManager.getItemById(id)
        view?.setName(item!!.name)
        view?.setPassword(item!!.password)
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
}