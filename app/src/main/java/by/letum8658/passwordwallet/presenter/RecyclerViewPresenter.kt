package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.ItemManager

class RecyclerViewPresenter {

    private var view: RecyclerViewView? = null

    fun setView(view: RecyclerViewView?) {
        this.view = view
    }

    fun getDataBase(): List<String> {
        val itemList = ItemManager.getItemList()
        if (itemList.isEmpty()) {
            val name = ItemManager.getName()!!
            ItemManager.getAllNames(name, object : Callback() {
                override fun returnResult(text: String?) {
                    view?.updateDatabase()
                }
            })
        }
        return itemList
    }

    fun getSearchList(): List<String> {
        val text = view?.getSearchString()
        return ItemManager.getSearchList(text!!)
    }

    fun fab() {
        view?.fab()
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}