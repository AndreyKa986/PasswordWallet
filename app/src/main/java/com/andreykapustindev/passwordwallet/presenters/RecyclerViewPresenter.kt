package com.andreykapustindev.passwordwallet.presenters

import com.andreykapustindev.passwordwallet.model.EntityRepository
import com.andreykapustindev.passwordwallet.view.views.RecyclerViewView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class RecyclerViewPresenter {

    private var view: RecyclerViewView? = null
    private var disposable: Disposable? = null

    fun setView(view: RecyclerViewView?) {
        this.view = view
    }

    fun getDataBase(): List<String> {
        val itemList = EntityRepository.getItemList()
        if (itemList.isEmpty()) {
            view?.let { itView ->
                val name = EntityRepository.getName()!!
                itView.progressBarOn()
                disposable = EntityRepository.getAllNames(name)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        EntityRepository.setItemList(it.toMutableList())
                        itView.progressBarOff()
                        itView.updateDatabase()
                    }, {
                        itView.progressBarOff()
                    })
            }
        }
        return itemList
    }

    fun getSearchList(text: String): List<String> = EntityRepository.getSearchList(text)

    fun detach() {
        disposable?.dispose()
        view = null
    }
}