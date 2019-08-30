package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.utils.encode
import by.letum8658.passwordwallet.view.views.ChangePasswordView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ChangePasswordPresenter {

    private var view: ChangePasswordView? = null
    private var disposable: Disposable? = null

    fun setView(view: ChangePasswordView?) {
        this.view = view
    }

    fun showItem(name: String) {
        view?.setName(name)
    }

    fun saveItem(itemName: String, password: String, confirmP: String) {
        if (password == confirmP) {
            val account = EntityManager.getName()!!
            val cryptPassword = encode(password)
            view?.progressBarOn()
            disposable = EntityManager.updateItem(account, itemName, Item(cryptPassword))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val list = arrayListOf(itemName, password)
                    view?.progressBarOff()
                    view?.onSaveClick(list)
                }, {
                    view?.progressBarOff()
                    view?.showMessage(1)
                })
        } else {
            view?.showMessage(2)
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}