package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.Item
import io.reactivex.Completable
import io.reactivex.Single

interface ItemRepository {

    fun getAllItems(): Single<MutableList<Item>>
    fun getItemPassword(): Single<String>
    fun saveNewItem(): Single<Item>
    fun updateItem(): Completable
    fun deleteItem(): Completable
}