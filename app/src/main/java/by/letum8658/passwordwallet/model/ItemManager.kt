package by.letum8658.passwordwallet.model

import by.letum8658.passwordwallet.net.provideItemRepository
import io.reactivex.schedulers.Schedulers

object ItemManager {

    private var itemName: String? = null
    private var itemList: MutableList<String> = mutableListOf()
    private var list: ArrayList<String> = arrayListOf()
    private val repository = provideItemRepository()

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

    fun getList(): ArrayList<String> = list

    fun setList(list: ArrayList<String>) {
        this.list = list
    }

    fun getSearchList(string: String): List<String> =
        itemList.filter { it.contains(string, true) }

    fun getAccount(account: String) = repository
        .getAccount(account)

    fun createAccount(account: String, password: User) = repository
        .createAccount(account, password)

    fun getAllNames(account: String) = repository
        .getAllNames(account)
        .subscribeOn(Schedulers.io())

    fun updateAllNames(account: String, list: List<String>) = repository
        .updateAllNames(account, list)
        .subscribeOn(Schedulers.io())

    fun getItemPassword(account: String, itemName: String) = repository
        .getItemPassword(account, itemName)

    fun saveNewItem(account: String, itemName: String, item: Item) = repository
        .saveNewItem(account, itemName, item)
        .subscribeOn(Schedulers.io())

    fun updateItem(account: String, itemName: String, item: Item) = repository
        .updateItem(account, itemName, item)
        .subscribeOn(Schedulers.io())

    fun deleteItem(account: String, itemName: String) = repository
        .deleteItem(account, itemName)
        .subscribeOn(Schedulers.io())
}