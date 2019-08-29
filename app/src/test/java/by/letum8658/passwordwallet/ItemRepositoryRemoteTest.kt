package by.letum8658.passwordwallet

import by.letum8658.passwordwallet.model.Item
import by.letum8658.passwordwallet.model.User
import by.letum8658.passwordwallet.net.ItemApi
import by.letum8658.passwordwallet.net.ItemRepositoryRemote
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ItemRepositoryRemoteTest {

    @Mock
    lateinit var api: ItemApi
    private var list: MutableList<String> = mutableListOf()

    init {
        MockitoAnnotations.initMocks(this)
        list.add("as")
        list.add("sd")
        list.add("df")
    }

    @Test
    fun getAccount() {

        Mockito
            .`when`(api.getAccount("qw"))
            .thenReturn(Single.just("word"))

        val repository = ItemRepositoryRemote(api)

        val password = repository.getAccount("qw")

        Assert.assertTrue(password.blockingGet() == "word")
        Assert.assertFalse(password.blockingGet() == "text")
    }

    @Test
    fun createAccount() {

        Mockito
            .`when`(api.createAccount("qw", User("we")))
            .thenReturn(Single.just(User("password")))

        val repository = ItemRepositoryRemote(api)

        val item = repository.createAccount("qw", User("we"))

        Assert.assertTrue(item.blockingGet().password == "password")
        Assert.assertFalse(item.blockingGet().password == "text")
    }

    @Test
    fun getAllNames() {

        Mockito
            .`when`(api.getAllNames("qw"))
            .thenReturn(Single.just(list))

        val repository = ItemRepositoryRemote(api)

        val itemList = repository.getAllNames("qw")

        Assert.assertTrue(itemList.blockingGet().size == 3)
        Assert.assertFalse(itemList.blockingGet().size == 7)
        Assert.assertTrue(itemList.blockingGet()[0] == "as")
        Assert.assertFalse(itemList.blockingGet()[2] == "jkl")
    }

    @Test
    fun updateAllNames() {

        Mockito
            .`when`(api.updateAllNames("jkl", list))
            .thenReturn(Completable.complete())

        val repository = ItemRepositoryRemote(api)

        val answer = repository.updateAllNames("jkl", list)

        Assert.assertTrue(answer.blockingGet() == null)
    }

    @Test
    fun getItemPassword() {

        Mockito
            .`when`(api.getItemPassword("qw", "we"))
            .thenReturn(Single.just(Item("password")))

        val repository = ItemRepositoryRemote(api)

        val item = repository.getItemPassword("qw", "we")

        Assert.assertTrue(item.blockingGet().password == "password")
        Assert.assertFalse(item.blockingGet().password == "text")
    }

    @Test
    fun saveNewItem() {

        Mockito
            .`when`(api.saveNewItem("asd", "qwe", Item("zxc")))
            .thenReturn(Single.just(Item("fg")))

        val repository = ItemRepositoryRemote(api)

        val item = repository.saveNewItem("asd", "qwe", Item("zxc"))

        Assert.assertTrue(item.blockingGet().password == "fg")
        Assert.assertFalse(item.blockingGet().password == "name")
    }

    @Test
    fun updateItem() {

        Mockito
            .`when`(api.updateItem("fgh", "rty", Item("vbn")))
            .thenReturn(Single.just(Item("hj")))

        val repository = ItemRepositoryRemote(api)

        val item = repository.updateItem("fgh", "rty", Item("vbn"))

        Assert.assertTrue(item.blockingGet().password == "hj")
        Assert.assertFalse(item.blockingGet().password == "yit")
    }

    @Test
    fun deleteItem() {

        Mockito
            .`when`(api.deleteItem("jkl", "uio"))
            .thenReturn(Completable.complete())

        val repository = ItemRepositoryRemote(api)

        val answer = repository.deleteItem("jkl", "uio")

        Assert.assertTrue(answer.blockingGet() == null)
    }
}
