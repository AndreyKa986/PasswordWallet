package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.EntityRepository
import by.letum8658.passwordwallet.utils.encode
import by.letum8658.passwordwallet.view.views.ChangePasswordView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ChangePasswordPresenter {

    companion object {
        private const val ERROR = 1
        private const val PASSWORD = 2
        private const val LOGIN = 3
    }

    private var view: ChangePasswordView? = null
    private var disposable: Disposable? = null

    fun setView(view: ChangePasswordView?) {
        this.view = view
    }

    fun saveItem(
        itemName: String,
        login: String,
        confirmLogin: String,
        password: String,
        confirmPassword: String
    ) {
        view?.let { itView ->
            if (login == confirmLogin) {
                if (password == confirmPassword) {
                    val account = EntityRepository.getName()!!
                    val cryptLogin = encode(login)
                    val cryptPassword = encode(password)
                    itView.progressBarOn()
                    disposable = EntityRepository.updateItem(
                        account,
                        itemName,
                        Item(cryptLogin, cryptPassword)
                    )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val list = arrayListOf(itemName, login, password)
                            itView.progressBarOff()
                            itView.onSaveClick(list)
                        }, {
                            itView.progressBarOff()
                            itView.showMessage(ERROR)
                        })
                } else {
                    itView.showMessage(PASSWORD)
                }
            } else {
                itView.showMessage(LOGIN)
            }
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}