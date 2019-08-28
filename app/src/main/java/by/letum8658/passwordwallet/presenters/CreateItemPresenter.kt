package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.ItemManager
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

    fun createPassword() {
        val name = view?.getName()
        view?.createPassword(name!!)
    }

    fun saveItem() {
        val itemName = view?.getName()!!
        val password = view?.getPassword()!!
        val confirm = view?.getConfirmPassword()
        if (itemName.isNotBlank()) {
            if (password == confirm) {
                val account = ItemManager.getName()!!
                val cryptPassword = encode(password)
                val list = ItemManager.getItemList()
                if (list.contains(itemName)) {
                    view?.showMessage(3)
                } else {
                    list.add(itemName)
                    ItemManager.setItemList(list)
                    view?.progressBarOn()
                    disposable = Single.zip(
                        ItemManager.updateAllNames(account, list).toSingleDefault(Unit)
                            .subscribeOn(Schedulers.computation()),
                        ItemManager.saveNewItem(account, itemName, Item(cryptPassword))
                            .subscribeOn(Schedulers.computation()),
                        BiFunction<Unit, Item, Item> { _, Item -> Item }
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view?.progressBarOff()
                            view?.saveItem()
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