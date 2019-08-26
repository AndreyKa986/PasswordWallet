package by.letum8658.passwordwallet

import by.letum8658.passwordwallet.net.provideItemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object ItemManager {

    private var itemName: String? = null
    private var itemList: MutableList<String> = mutableListOf()
    private val repository = provideItemRepository()
    private var disposable: Disposable? = null

    fun getName(): String? = itemName

    fun setName(text: String) {
        itemName = text
    }

    fun getItemList(): MutableList<String> = itemList

    fun setItemList(list: MutableList<String>) {
        list.sort()
        itemList = list
    }

    fun clearItemList() {
        itemList.clear()
    }

    fun getSearchList(string: String): List<String> =
        itemList.filter { it.contains(string, true) }

    fun getAccount(account: String, callback: Callback) {
        disposable = repository
            .getAccount(account)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.returnResult(it)
            }, {
                callback.returnResult(null)
            })
    }

    fun createAccount(account: String, password: User, callback: Callback) {
        disposable = repository
            .createAccount(account, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.returnResult("ok")
            }, {})
    }

    fun getAllNames(account: String, callback: Callback) {
        disposable = repository
            .getAllNames(account)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itemList.addAll(it)
                callback.returnResult("ok")
            }, {})
    }

    fun updateAllNames(account: String, list: List<String>) {
        disposable = repository
            .updateAllNames(account, list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getItemPassword(account: String, itemName: String, callback: Callback) {
        disposable = repository
            .getItemPassword(account, itemName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.returnResult(it.password)
            }, {})
    }

    fun saveNewItem(account: String, itemName: String, item: Item, callback: Callback) {
        disposable = repository
            .saveNewItem(account, itemName, item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.returnResult("ok")
            }, {})
    }

    fun updateItem(account: String, itemName: String, item: Item, callback: Callback) {
        disposable = repository
            .updateItem(account, itemName, item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.returnResult("ok")
            }, {})
    }

    fun deleteItem(account: String, itemName: String, callback: Callback) {
        disposable = repository
            .deleteItem(account, itemName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.returnResult("ok")
            }, {})
    }

    fun dispose() {
        disposable?.dispose()
    }
}