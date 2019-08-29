package by.letum8658.passwordwallet

import by.letum8658.passwordwallet.utils.decode
import by.letum8658.passwordwallet.utils.encode
import org.junit.Assert
import org.junit.Test

class EncryptionTest {

    @Test
    fun isCorrectEncryption() {

        Assert.assertEquals("asdfghj", crypt("asdfghj"))
        Assert.assertEquals("00", crypt("00"))
        Assert.assertEquals("REasd09 df I4", crypt("REasd09 df I4"))
        Assert.assertNotEquals("dfgh", crypt("sdgh"))
        Assert.assertNotEquals("oo", crypt("OO"))
        Assert.assertNotEquals("string", crypt("text"))
        Assert.assertEquals("__=__", crypt("__=__"))
    }

    fun crypt(string: String): String {
        val text = encode(string)
        return decode(text)
    }
}