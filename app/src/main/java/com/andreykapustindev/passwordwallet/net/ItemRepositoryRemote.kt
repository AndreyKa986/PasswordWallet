package com.andreykapustindev.passwordwallet.net

import com.andreykapustindev.passwordwallet.model.Item
import io.reactivex.Completable
import io.reactivex.Single

class ItemRepositoryRemote(private val api: ItemApi) : ItemRepository {

    override fun getItemPassword(account: String, itemName: String): Single<Item> =
        api.getItemPassword(account, itemName)

    override fun saveNewItem(account: String, itemName: String, item: Item): Single<Item> =
        api.saveNewItem(account, itemName, item)

    override fun updateItem(account: String, itemName: String, item: Item): Single<Item> =
        api.updateItem(account, itemName, item)

    override fun deleteItem(account: String, itemName: String): Completable =
        api.deleteItem(account, itemName)
}