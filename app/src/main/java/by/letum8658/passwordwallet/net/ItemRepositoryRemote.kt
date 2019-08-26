package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.User
import io.reactivex.Completable
import io.reactivex.Single

class ItemRepositoryRemote(private val api: ItemApi) : ItemRepository {

    override fun getAccount(account: String): Single<String> =
        api.getAccount(account)

    override fun createAccount(account: String, password: User): Single<User> =
        api.createAccount(account, password)

    override fun getAllNames(account: String): Single<List<String>> =
        api.getAllNames(account)

    override fun updateAllNames(account: String, list: List<String>): Completable =
        api.updateAllNames(account, list)

    override fun getItemPassword(account: String, itemName: String): Single<Item> =
        api.getItemPassword(account, itemName)

    override fun saveNewItem(account: String, itemName: String, item: Item): Single<Item> =
        api.saveNewItem(account, itemName, item)

    override fun updateItem(account: String, itemName: String, item: Item): Single<Item> =
        api.updateItem(account, itemName, item)

    override fun deleteItem(account: String, itemName: String): Completable =
        api.deleteItem(account, itemName)
}