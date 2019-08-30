package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.utils.encode
import by.letum8658.passwordwallet.view.views.CreateItemView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class CreateItemPresenter {

    private var view: CreateItemView? = null
    private var disposable: Disposable? = null

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

    fun saveItem(itemName: String, password: String, confirm: String) {
        if (itemName.isNotBlank()) {
            if (password == confirm) {
                val account = EntityManager.getName()!!
                val cryptPassword = encode(password)
                val list = EntityManager.getItemList()
                if (list.contains(itemName)) {
                    view?.showMessage(3)
                } else {
                    list.add(itemName)
                    EntityManager.setItemList(list)
                    view?.progressBarOn()
                    disposable = Single.zip(
                        EntityManager.updateAllNames(account, list).toSingleDefault(Unit)
                            .subscribeOn(Schedulers.computation()),
                        EntityManager.saveNewItem(account, itemName, Item(cryptPassword))
                            .subscribeOn(Schedulers.computation()),
                        BiFunction<Unit, Item, Item> { _, Item -> Item }
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view?.progressBarOff()
                            view?.onSaveItemClick()
                        }, {
                            view?.progressBarOff()
                            view?.showMessage(4)
                        })
                }
            } else {
                view?.showMessage(1)
            }
        } else {
            view?.showMessage(2)
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}