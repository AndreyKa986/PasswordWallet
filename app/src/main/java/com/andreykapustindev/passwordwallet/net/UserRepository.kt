package com.andreykapustindev.passwordwallet.net

import com.andreykapustindev.passwordwallet.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun getAccount(account: String): Single<String>
    fun createAccount(account: String, password: User): Single<User>
    fun getAllNames(account: String): Single<List<String>>
    fun updateAllNames(account: String, list: List<String>): Completable
}