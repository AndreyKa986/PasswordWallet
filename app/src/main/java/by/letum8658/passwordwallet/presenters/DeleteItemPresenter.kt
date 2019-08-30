package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.view.views.DeleteItemView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class DeleteItemPresenter {

    private var view: DeleteItemView? = null
    private var disposable: Disposable? = null

    fun setView(view: DeleteItemView?) {
        this.view = view
    }

    fun yes(itemName: String) {
        val list = EntityManager.getItemList()
        list.remove(itemName)
        EntityManager.setItemList(list)
        val account = EntityManager.getName()!!
        view?.progressBarOn()
        disposable = Single.zip(
            EntityManager.updateAllNames(account, list).toSingleDefault(Unit)
                .subscribeOn(Schedulers.computation()),
            EntityManager.deleteItem(account, itemName).toSingleDefault(Unit)
                .subscribeOn(Schedulers.computation()),
            BiFunction<Unit, Unit, Unit> { _, _ -> Unit }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.progressBarOff()
                view?.onYesClick()
            }, {
                view?.progressBarOff()
                view?.showMessage()
            })
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}