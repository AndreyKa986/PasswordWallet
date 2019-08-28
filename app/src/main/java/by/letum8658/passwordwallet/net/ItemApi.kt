package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.DELETE

interface ItemApi {

    @GET("{name}/password/.json")
    fun getAccount(
        @Path("name") account: String
    ): Single<String>

    @PUT("{name}/.json")
    fun createAccount(
        @Path("name") account: String,
        @Body password: User
    ): Single<User>

    @GET("{name}/namesList/.json")
    fun getAllNames(
        @Path("name") account: String
    ): Single<List<String>>

    @PUT("{name}/namesList/.json")
    fun updateAllNames(
        @Path("name") account: String,
        @Body list: List<String>
    ): Completable

    @GET("{name}/items/{item}/.json")
    fun getItemPassword(
        @Path("name") account: String,
        @Path("item") itemName: String
    ): Single<Item>

    @PATCH("{name}/items/{item}.json")
    fun saveNewItem(
        @Path("name") account: String,
        @Path("item") itemName: String,
        @Body item: Item
    ): Single<Item>

    @PATCH("{name}/items/{item}.json")
    fun updateItem(
        @Path("name") account: String,
        @Path("item") itemName: String,
        @Body item: Item
    ): Single<Item>

    @DELETE("{name}/items/{item}/.json")
    fun deleteItem(
        @Path("name") account: String,
        @Path("item") itemName: String
    ): Completable
}