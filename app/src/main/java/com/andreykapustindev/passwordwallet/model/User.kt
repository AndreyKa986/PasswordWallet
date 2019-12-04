package com.andreykapustindev.passwordwallet.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("password")
    val password: String
)