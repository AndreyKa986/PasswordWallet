package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.model.Item
import io.reactivex.Completable
import io.reactivex.Single

interface ItemRepository {

    fun getItemPassword(account: String, itemName: String): Single<Item>
    fun saveNewItem(account: String, itemName: String, item: Item): Single<Item>
    fun updateItem(account: String, itemName: String, item: Item): Single<Item>
    fun deleteItem(account: String, itemName: String): Completable
}