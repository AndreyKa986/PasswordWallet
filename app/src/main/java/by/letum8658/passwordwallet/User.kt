package by.letum8658.passwordwallet

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("password")
    val password: String
)