package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.Item
import io.reactivex.Completable
import io.reactivex.Single

class ItemRepositoryRemote(private val api: ItemApi) : ItemRepository {

    override fun getAllItems(): Single<MutableList<Item>> = api.getAllItems()

    override fun getItemPassword(): Single<String> = api.getItemPassword()

    override fun saveNewItem(): Single<Item> = api.saveNewItem()

    override fun updateItem(): Completable = api.updateItem()

    override fun deleteItem(): Completable = api.deleteItem()
}