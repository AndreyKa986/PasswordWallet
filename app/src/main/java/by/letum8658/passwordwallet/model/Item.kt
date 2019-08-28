package by.letum8658.passwordwallet.model

import com.google.gson.annotations.SerializedName

data class Item(

    @SerializedName("password")
    val password: String
)