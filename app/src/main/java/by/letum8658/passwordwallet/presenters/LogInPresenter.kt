package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.utils.AppPrefManager
import by.letum8658.passwordwallet.model.EntityRepository
import by.letum8658.passwordwallet.utils.decode
import by.letum8658.passwordwallet.view.views.LogInView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LogInPresenter {

    companion object {

        private const val INCORRECTLY = 1
        private const val DO_NOT_HAVE = 2
        private const val PASSWORD = 3
        private const val USERNAME = 4
        private const val ERROR = 5
    }

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
        view?.let { itView ->
            if (name.isNotBlank()) {
                if (password.isNotBlank()) {
                    itView.progressBarOn()
                    disposable = EntityRepository.getAccount(name)
                        .map { decode(it) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (password == it) {
                                EntityRepository.setName(name)
                                if (EntityRepository.getItemList().isNotEmpty()) {
                                    EntityRepository.clearItemList()
                                }
                                itView.progressBarOff()
                                itView.onLogInClick()
                            } else {
                                itView.progressBarOff()
                                itView.showMessage(INCORRECTLY)
                            }
                        }, {
                            if (it is NoSuchElementException) {
                                itView.progressBarOff()
                                itView.showMessage(DO_NOT_HAVE)
                            } else {
                                itView.progressBarOff()
                                itView.showMessage(ERROR)
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