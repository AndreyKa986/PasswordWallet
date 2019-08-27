package by.letum8658.passwordwallet

object BackPressed {

    private var list: ArrayList<String> = arrayListOf()

    fun getList(): ArrayList<String> = list

    fun setList(list: ArrayList<String>) {
        this.list = list
    }
}