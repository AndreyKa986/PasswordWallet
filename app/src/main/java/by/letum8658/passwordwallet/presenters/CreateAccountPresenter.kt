package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.ItemManager
import by.letum8658.passwordwallet.model.User
import by.letum8658.passwordwallet.view.views.CreateAccountView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateAccountPresenter {

    private var view: CreateAccountView? = null
    private var disposable: Disposable? = null
    private var isFreeName = true

    fun setView(view: CreateAccountView?) {
        this.view = view
    }

    fun createAccount() {
        val name = view?.getName()
        val password = view?.getPassword()!!
        val confirmPassword = view?.getConfirmPassword()
        if (name!!.isNotBlank()) {
            if (password == confirmPassword) {
                view?.progressBarOn()
                disposable = ItemManager.getAccount(name)
                    .onErrorResumeNext {
                        if (it is NoSuchElementException) {
                            Single.just("NULL")
                        } else {
                            Single.error(it)
                        }
                    }.flatMap {
                        if (it == "NULL") {
                            ItemManager.createAccount(name, User(password))
                        } else {
                            isFreeName = false
                            Single.error(Throwable("Account exists"))
                        }
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        ItemManager.setName(name)
                        view?.progressBarOff()
                        view?.createAccount()
                    }, {
                        if (isFreeName) {
                            view?.progressBarOff()
                            view?.showMessage(4)
                        } else {
                            view?.progressBarOff()
                            view?.showMessage(1)
                        }
                    })
            } else {
                view?.showMessage(2)
            }
        } else {
            view?.showMessage(3)
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}