package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.EntityRepository
import by.letum8658.passwordwallet.utils.decode
import by.letum8658.passwordwallet.view.views.InformationView
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

    fun showData(item: String, pass: String?, list: ArrayList<String>?) {
        view?.let { itView ->
            if (item.isNotBlank()) {
                itView.setName(item)
                if (pass == null) {
                    val name = EntityRepository.getName()!!
                    itView.progressBarOn()
                    disposable = EntityRepository.getItemPassword(name, item)
                        .map { Item(decode(it.password)) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val password = it.password
                            itView.progressBarOff()
                            itView.setPassword(password)
                        }, {
                            itView.progressBarOff()
                            itView.showMessage()
                        })
                } else {
                    itView.setPassword(pass)
                }
            } else {
                itView.setName(list!![0])
                itView.setPassword(list[1])
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