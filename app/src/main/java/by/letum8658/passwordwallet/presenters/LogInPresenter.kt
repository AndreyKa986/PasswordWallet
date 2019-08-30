package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.AppPrefManager
import by.letum8658.passwordwallet.model.ItemManager
import by.letum8658.passwordwallet.utils.decode
import by.letum8658.passwordwallet.view.views.LogInView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LogInPresenter {

    private var view: LogInView? = null
    private var disposable: Disposable? = null
    private lateinit var prefsManager: AppPrefManager

    fun setView(view: LogInView?) {
        this.view = view
        prefsManager = view?.getPrefsManager()!!
    }

    fun setName() {
        val name = prefsManager.getName()
        view?.setName(name)
    }

    fun saveName(name: String) {
        prefsManager.saveName(name)
    }

    fun logIn(name: String, password: String) {
        if (name.isNotBlank()) {
            if (password.isNotBlank()) {
                view?.progressBarOn()
                disposable = ItemManager.getAccount(name)
                    .map { decode(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (password == it) {
                            ItemManager.setName(name)
                            if (ItemManager.getItemList().isNotEmpty()) {
                                ItemManager.clearItemList()
                            }
                            view?.progressBarOff()
                            view?.onLogInClick()
                        } else {
                            view?.progressBarOff()
                            view?.showMessage(1)
                        }
                    }, {
                        if (it is NoSuchElementException) {
                            view?.progressBarOff()
                            view?.showMessage(2)
                        } else {
                            view?.progressBarOff()
                            view?.showMessage(5)
                        }
                    })
            } else {
                view?.showMessage(3)
            }
        } else {
            view?.showMessage(4)
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}