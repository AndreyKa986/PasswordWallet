package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.utils.AppPrefManager
import by.letum8658.passwordwallet.model.EntityRepository
import by.letum8658.passwordwallet.model.User
import by.letum8658.passwordwallet.utils.encode
import by.letum8658.passwordwallet.view.views.CreateAccountView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateAccountPresenter {

    companion object {

        private const val TAKEN_NAME = 1
        private const val PASSWORD = 2
        private const val USERNAME = 3
        private const val ERROR = 4
    }

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
        view?.let { itView ->
            if (name.isNotBlank()) {
                if (password == confirmPassword) {
                    val cryptPassword = encode(password)
                    itView.progressBarOn()
                    disposable = EntityRepository.getAccount(name)
                        .onErrorResumeNext {
                            if (it is NoSuchElementException) {
                                Single.just("NULL")
                            } else {
                                Single.error(it)
                            }
                        }.flatMap {
                            if (it == "NULL") {
                                EntityRepository.createAccount(name, User(cryptPassword))
                            } else {
                                isFreeName = false
                                Single.error(Throwable("Account exists"))
                            }
                        }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            EntityRepository.setName(name)
                            if (EntityRepository.getItemList().isNotEmpty()) {
                                EntityRepository.clearItemList()
                            }
                            itView.progressBarOff()
                            itView.onCreateAccountClick()
                        }, {
                            if (isFreeName) {
                                itView.progressBarOff()
                                itView.showMessage(ERROR)
                            } else {
                                itView.progressBarOff()
                                itView.showMessage(TAKEN_NAME)
                            }
                        })
                } else {
                    itView.showMessage(PASSWORD)
                }
            } else {
                itView.showMessage(USERNAME)
            }
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}