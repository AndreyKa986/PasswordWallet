package by.letum8658.passwordwallet

import com.google.gson.annotations.SerializedName

data class Item(

    @SerializedName("name")
    val name: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("id")
    val id: Int
)