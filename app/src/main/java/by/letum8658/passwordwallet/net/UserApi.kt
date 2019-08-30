package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

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
}