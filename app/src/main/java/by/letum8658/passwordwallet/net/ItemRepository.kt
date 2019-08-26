package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.User
import io.reactivex.Completable
import io.reactivex.Single

interface ItemRepository {

    fun getAccount(account: String): Single<String>
    fun createAccount(account: String, password: User): Single<User>
    fun getAllNames(account: String): Single<List<String>>
    fun updateAllNames(account: String, list: List<String>): Completable
    fun getItemPassword(account: String, itemName: String): Single<Item>
    fun saveNewItem(account: String, itemName: String, item: Item): Single<Item>
    fun updateItem(account: String, itemName: String, item: Item): Single<Item>
    fun deleteItem(account: String, itemName: String): Completable
}