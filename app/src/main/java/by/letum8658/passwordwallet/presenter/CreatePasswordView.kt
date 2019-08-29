package by.letum8658.passwordwallet.presenter

interface CreatePasswordView {

    fun setPassword(password: String)
    fun getPassword(): String
    fun savePassword(list: ArrayList<String>)
}