package by.letum8658.passwordwallet.presenter

interface InformationView {

    fun setName(name: String)
    fun setPassword(password: String)
    fun delete()
    fun change()
    fun ok()
}