package com.andreykapustindev.passwordwallet.net

import com.andreykapustindev.passwordwallet.model.Item
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.DELETE

interface ItemApi {

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