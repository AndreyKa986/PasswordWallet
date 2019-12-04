package com.andreykapustindev.passwordwallet.presenters

import com.andreykapustindev.passwordwallet.model.Item
import com.andreykapustindev.passwordwallet.model.EntityRepository
import com.andreykapustindev.passwordwallet.utils.encode
import com.andreykapustindev.passwordwallet.view.views.ChangePasswordView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ChangePasswordPresenter {

    companion object {
        private const val ERROR = 1
        private const val PASSWORD = 2
    }

    private var view: ChangePasswordView? = null
    private var disposable: Disposable? = null

    fun setView(view: ChangePasswordView?) {
        this.view = view
    }

    fun saveItem(
        itemName: String,
        login: String,
        password: String,
        confirmPassword: String
    ) {
        view?.let { itView ->
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
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}