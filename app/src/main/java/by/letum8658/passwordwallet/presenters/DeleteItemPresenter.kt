package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.model.EntityRepository
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
        val list = EntityRepository.getItemList()
        list.remove(itemName)
        EntityRepository.setItemList(list)
        val account = EntityRepository.getName()!!
        view?.let { itView ->
            itView.progressBarOn()
            disposable = Single.zip(
                EntityRepository.updateAllNames(account, list).toSingleDefault(Unit)
                    .subscribeOn(Schedulers.computation()),
                EntityRepository.deleteItem(account, itemName).toSingleDefault(Unit)
                    .subscribeOn(Schedulers.computation()),
                BiFunction<Unit, Unit, Unit> { _, _ -> Unit }
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itView.progressBarOff()
                    itView.onYesClick()
                }, {
                    itView.progressBarOff()
                    itView.showMessage()
                })
        }
    }

    fun detach() {
        disposable?.dispose()
        view = null
    }
}