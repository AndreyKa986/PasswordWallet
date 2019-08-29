package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.Item
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST

interface ItemApi {

    @GET
    fun getAllItems(): Single<MutableList<Item>>

    @POST
    fun getItemPassword(): Single<String>

    @POST
    fun saveNewItem(): Single<Item>

    @POST
    fun updateItem(): Completable

    @POST
    fun deleteItem(): Completable
}