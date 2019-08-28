package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.ItemManager
import by.letum8658.passwordwallet.utils.decode
import by.letum8658.passwordwallet.view.views.InformationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class InformationPresenter {

    private var view: InformationView? = null
    private var disposable: Disposable? = null

    fun setView(view: InformationView?) {
        this.view = view
    }

    fun showData(item: String) {
        if (item.isNotBlank()) {
            view?.setName(item)
            val name = ItemManager.getName()!!
            view?.progressBarOn()
            disposable = ItemManager.getItemPassword(name, item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val password = decode(it.password)
                    view?.progressBarOff()
                    view?.setPassword(password)
                }, {
                    view?.progressBarOff()
                    view?.showMessage()
                })
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