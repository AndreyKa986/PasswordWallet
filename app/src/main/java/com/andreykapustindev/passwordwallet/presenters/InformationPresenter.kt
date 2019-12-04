package com.andreykapustindev.passwordwallet.presenters

import com.andreykapustindev.passwordwallet.model.Item
import com.andreykapustindev.passwordwallet.model.EntityRepository
import com.andreykapustindev.passwordwallet.utils.decode
import com.andreykapustindev.passwordwallet.view.views.InformationView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class InformationPresenter {

    private var view: InformationView? = null
    private var disposable: Disposable? = null

    fun setView(view: InformationView?) {
        this.view = view
    }

    fun showData(item: String, array: ArrayList<String>?, list: ArrayList<String>?) {
        view?.let { itView ->
            if (item.isNotBlank()) {
                if (array == null) {
                    val name = EntityRepository.getName()!!
                    itView.progressBarOn()
                    disposable = EntityRepository.getItemInformation(name, item)
                        .map { Item(decode(it.login), decode(it.password)) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val login = it.login
                            val password = it.password
                            itView.progressBarOff()
                            itView.setLogin(login)
                            itView.setPassword(password)
                        }, {
                            itView.progressBarOff()
                            itView.showMessage()
                        })
                } else {
                    itView.setLogin(array[0])
                    itView.setPassword(array[1])
                }
            } else {
                list?.apply {
                    itView.setLogin(this[1])
                    itView.setPassword(this[2])
                }
            }
        }
    }

    fun deleteItem(itemName: String) {
        val list = EntityRepository.getItemList()
        list.remove(itemName)
        EntityRepository.setItemList(list)
        val account = EntityRepository.getName()!!
        view?.let { itView ->
            itView.progressBarOn()
            disposable = Single.zip(
                EntityRepository.updateAllNames(account, list).toSingleDefault(Unit)
                    .subscribeOn(Schedulers.computation()),
                EntityRepository.deleteItem(account, itemName).toSingleDefault(Unit)
                    .subscribeOn(Schedulers.computation()),
                BiFunction<Unit, Unit, Unit> { _, _ -> Unit }
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itView.progressBarOff()
                    itView.delete()
                }, {
                    itView.progressBarOff()
                    itView.showMessage()
                })
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}