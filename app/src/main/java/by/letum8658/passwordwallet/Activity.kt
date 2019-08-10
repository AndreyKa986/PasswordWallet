package by.letum8658.passwordwallet

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import by.letum8658.passwordwallet.ui.LogInFragment

class Activity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LogInFragment())
                .commit()
        }
    }
}