package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.ItemManager
import by.letum8658.passwordwallet.view.views.RecyclerViewView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class RecyclerViewPresenter {

    private var view: RecyclerViewView? = null
    private var disposable: Disposable? = null

    fun setView(view: RecyclerViewView?) {
        this.view = view
    }

    fun getDataBase(): List<String> {
        val itemList = ItemManager.getItemList()
        if (itemList.isEmpty()) {
            val name = ItemManager.getName()!!
            view?.progressBarOn()
            disposable = ItemManager.getAllNames(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ItemManager.setItemList(it.toMutableList())
                    view?.progressBarOff()
                    view?.updateDatabase()
                }, {
                    view?.progressBarOff()
                    view?.showMessage()
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
        disposable?.dispose()
        view = null
    }
}