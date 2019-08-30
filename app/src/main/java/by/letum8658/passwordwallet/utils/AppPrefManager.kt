package by.letum8658.passwordwallet.utils

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val TEXT_KEY = "text_key"

class AppPrefManager(private val context: Context) {

    private val sharedPrefs: SharedPreferences = context
        .getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun saveName(text: String) {
        sharedPrefs
            .edit()
            .putString(TEXT_KEY, text)
            .apply()
    }

    fun getName(): String {
        return sharedPrefs.getString(TEXT_KEY, "") ?: ""
    }
}