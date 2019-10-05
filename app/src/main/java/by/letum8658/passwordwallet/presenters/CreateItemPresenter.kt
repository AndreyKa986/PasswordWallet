package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.EntityRepository
import by.letum8658.passwordwallet.utils.encode
import by.letum8658.passwordwallet.view.views.CreateItemView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class CreateItemPresenter {

    companion object {

        private const val PASSWORD = 1
        private const val ITEMNAME = 2
        private const val TAKEN_ITEM = 3
        private const val ERROR = 4
        private const val LOGIN = 5
    }

    private var view: CreateItemView? = null
    private var disposable: Disposable? = null

    fun setView(view: CreateItemView?) {
        this.view = view
    }

    fun setData(list: ArrayList<String>?) {
        if (list != null) {
            if (list[0].isNotBlank()) view?.setName(list[0])
            if (list[1].isNotBlank()) view?.setLogin(list[1])
            if (list[1].isNotBlank()) {
                view?.setPassword(list[1])
                view?.setConfirmPassword(list[1])
            }
        }
    }

    fun saveItem(itemName: String, login: String, password: String, confirm: String) {
        view?.let { itView ->
            if (itemName.isNotBlank()) {
                if (login.isNotBlank()) {
                    if (password == confirm) {
                        val account = EntityRepository.getName()!!
                        val cryptLogin = encode(login)
                        val cryptPassword = encode(password)
                        val list = EntityRepository.getItemList()
                        if (list.contains(itemName)) {
                            itView.showMessage(TAKEN_ITEM)
                        } else {
                            list.add(itemName)
                            EntityRepository.setItemList(list)
                            itView.progressBarOn()
                            disposable = Single.zip(
                                EntityRepository.updateAllNames(account, list).toSingleDefault(Unit)
                                    .subscribeOn(Schedulers.computation()),
                                EntityRepository.saveNewItem(
                                    account,
                                    itemName,
                                    Item(cryptLogin, cryptPassword)
                                )
                                    .subscribeOn(Schedulers.computation()),
                                BiFunction<Unit, Item, Item> { _, Item -> Item }
                            ).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    itView.progressBarOff()
                                    itView.onSaveItemClick()
                                }, {
                                    itView.progressBarOff()
                                    itView.showMessage(ERROR)
                                })
                        }
                    } else {
                        itView.showMessage(PASSWORD)
                    }
                } else {
                    itView.showMessage(LOGIN)
                }
            } else {
                itView.showMessage(ITEMNAME)
            }
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}