package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.ItemManager

class DeleteItemPresenter {

    private var view: DeleteItemView? = null

    fun setView(view: DeleteItemView?) {
        this.view = view
    }

    fun no() {
        view?.no()
    }

    fun yes(itemName: String) {
        val list = ItemManager.getItemList()
        list.remove(itemName)
        ItemManager.setItemList(list)
        val account = ItemManager.getName()!!
        ItemManager.updateAllNames(account, list)
        ItemManager.deleteItem(account, itemName, object : Callback() {
            override fun returnResult(text: String?) {
                view?.yes()
            }
        })
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}