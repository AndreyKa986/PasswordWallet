package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.ItemManager
import by.letum8658.passwordwallet.utils.decode
import by.letum8658.passwordwallet.view.views.InformationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InformationPresenter {

    private var view: InformationView? = null
    private var disposable: Disposable? = null

    fun setView(view: InformationView?) {
        this.view = view
    }

    fun showData(item: String, pass: String?) {
        if (item.isNotBlank()) {
            view?.setName(item)
            if (pass == null) {
                val name = ItemManager.getName()!!
                view?.progressBarOn()
                disposable = ItemManager.getItemPassword(name, item)
                    .map { Item(decode(it.password)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val password = it.password
                        view?.progressBarOff()
                        view?.setPassword(password)
                    }, {
                        view?.progressBarOff()
                        view?.showMessage()
                    })
            } else {
                view?.setPassword(pass)
            }
        } else {
            val list = view?.getInformationList()!!
            view?.setName(list[0])
            view?.setPassword(list[1])
        }
    }

    fun delete() {
        view?.delete()
    }

    fun change() {
        view?.change()
    }

    fun ok() {
        view?.ok()
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}