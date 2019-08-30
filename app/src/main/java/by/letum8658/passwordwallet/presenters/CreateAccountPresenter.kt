package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.utils.AppPrefManager
import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.model.User
import by.letum8658.passwordwallet.utils.encode
import by.letum8658.passwordwallet.view.views.CreateAccountView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateAccountPresenter {

    private var view: CreateAccountView? = null
    private var disposable: Disposable? = null
    private lateinit var prefsManager: AppPrefManager
    private var isFreeName = true

    fun setView(view: CreateAccountView?) {
        this.view = view
        prefsManager = view?.getPrefsManager()!!
    }

    fun saveName(name: String) {
        prefsManager.saveName(name)
    }

    fun createAccount(name: String, password: String, confirmPassword: String) {
        if (name.isNotBlank()) {
            if (password == confirmPassword) {
                val cryptPassword = encode(password)
                view?.progressBarOn()
                disposable = EntityManager.getAccount(name)
                    .onErrorResumeNext {
                        if (it is NoSuchElementException) {
                            Single.just("NULL")
                        } else {
                            Single.error(it)
                        }
                    }.flatMap {
                        if (it == "NULL") {
                            EntityManager.createAccount(name, User(cryptPassword))
                        } else {
                            isFreeName = false
                            Single.error(Throwable("Account exists"))
                        }
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        EntityManager.setName(name)
                        if (EntityManager.getItemList().isNotEmpty()) {
                            EntityManager.clearItemList()
                        }
                        view?.progressBarOff()
                        view?.onCreateAccountClick()
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