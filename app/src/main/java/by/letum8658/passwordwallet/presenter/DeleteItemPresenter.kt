package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.ItemManager

class DeleteItemPresenter {

    private var view: DeleteItemView? = null

    fun setView(view: DeleteItemView?) {
        this.view = view
    }

    fun no() {
        view?.no()
    }

    fun yes(id: Int) {
        val item = ItemManager.getItemById(id)
        ItemManager.deleteItem(item!!)
        view?.yes()
    }
}