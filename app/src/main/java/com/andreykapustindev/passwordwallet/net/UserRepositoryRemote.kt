package com.andreykapustindev.passwordwallet.net

import com.andreykapustindev.passwordwallet.model.User
import io.reactivex.Completable
import io.reactivex.Single

class UserRepositoryRemote(private val api: UserApi) : UserRepository {

    override fun getAccount(account: String): Single<String> =
        api.getAccount(account)

    override fun createAccount(account: String, password: User): Single<User> =
        api.createAccount(account, password)

    override fun getAllNames(account: String): Single<List<String>> =
        api.getAllNames(account)

    override fun updateAllNames(account: String, list: List<String>): Completable =
        api.updateAllNames(account, list)
}