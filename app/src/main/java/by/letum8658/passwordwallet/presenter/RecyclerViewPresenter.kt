package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager

class RecyclerViewPresenter {

    private var view: RecyclerViewView? = null

    fun setView(view: RecyclerViewView?) {
        this.view = view
    }

    fun getDataBase(): List<Item> = ItemManager.getItemList()

    fun getSearchList(): List<Item> {
        val text = view?.getSearchString()
        return ItemManager.getSearchList(text!!)
    }

    fun fab() {
        view?.fab()
    }
}