package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.EntityManager
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

    fun showData(item: String, pass: String?, list: ArrayList<String>?) {
        if (item.isNotBlank()) {
            view?.setName(item)
            if (pass == null) {
                val name = EntityManager.getName()!!
                view?.progressBarOn()
                disposable = EntityManager.getItemPassword(name, item)
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
            view?.setName(list!![0])
            view?.setPassword(list!![1])
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}