package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.EntityManager
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
        val itemList = EntityManager.getItemList()
        if (itemList.isEmpty()) {
            val name = EntityManager.getName()!!
            view?.progressBarOn()
            disposable = EntityManager.getAllNames(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    EntityManager.setItemList(it.toMutableList())
                    view?.progressBarOff()
                    view?.updateDatabase()
                }, {
                    view?.progressBarOff()
                })
        }
        return itemList
    }

    fun getSearchList(text: String): List<String> = EntityManager.getSearchList(text)

    fun detach() {
        disposable?.dispose()
        view = null
    }
}