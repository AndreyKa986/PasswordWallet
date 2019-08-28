package by.letum8658.passwordwallet

import by.letum8658.passwordwallet.model.Item
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
    private var list: MutableList<Item> = mutableListOf()

    init {
        MockitoAnnotations.initMocks(this)
        list.add(Item("as", "as", 1))
        list.add(Item("sd", "sd", 2))
        list.add(Item("df", "df", 3))
    }

    @Test
    fun getAllItems() {

        Mockito
            .`when`(api.getAllNames())
            .thenReturn(Single.just(list))

        val repository = ItemRepositoryRemote(api)

        val itemList = repository.getAllItems()

        Assert.assertTrue(itemList.blockingGet().size == 3)
        Assert.assertTrue(itemList.blockingGet()[0].name == "as")
        Assert.assertFalse(itemList.blockingGet()[2].id == 5)
    }

    @Test
    fun getItemPassword() {

        Mockito
            .`when`(api.getItemPassword())
            .thenReturn(Single.just("password"))

        val repository = ItemRepositoryRemote(api)

        val text = repository.getItemPassword()

        Assert.assertTrue(text.blockingGet() == "password")
        Assert.assertFalse(text.blockingGet() == "text")
    }

    @Test
    fun saveNewItem() {

        Mockito
            .`when`(api.saveNewItem())
            .thenReturn(Single.just(Item("fg", "fg", 4)))

        val repository = ItemRepositoryRemote(api)

        val item = repository.saveNewItem()

        Assert.assertTrue(item.blockingGet().password == "fg")
        Assert.assertFalse(item.blockingGet().name == "name")
        Assert.assertFalse(item.blockingGet().id == 6)
    }

    @Test
    fun updateItem() {

        Mockito
            .`when`(api.updateItem())
            .thenReturn(Completable.complete())

        val repository = ItemRepositoryRemote(api)

        val answer = repository.updateItem()

        Assert.assertTrue(answer.blockingGet() == null)
    }

    @Test
    fun deleteItem() {

        Mockito
            .`when`(api.deleteItem())
            .thenReturn(Completable.complete())

        val repository = ItemRepositoryRemote(api)

        val answer = repository.deleteItem()

        Assert.assertTrue(answer.blockingGet() == null)
    }
}
