package by.letum8658.passwordwallet

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import by.letum8658.passwordwallet.presenter.LogInFragment
import by.letum8658.passwordwallet.presenter.CreateAccountFragment
import by.letum8658.passwordwallet.presenter.RecyclerViewFragment
import by.letum8658.passwordwallet.presenter.CreateItemFragment
import by.letum8658.passwordwallet.presenter.InformationFragment
import by.letum8658.passwordwallet.presenter.ChangePasswordFragment
import by.letum8658.passwordwallet.presenter.DeleteItemFragment
import by.letum8658.passwordwallet.presenter.CreatePasswordFragment

class Activity : FragmentActivity(),
    LogInFragment.Listener,
    CreateAccountFragment.Listener,
    RecyclerViewFragment.Listener,
    CreateItemFragment.Listener,
    InformationFragment.Listener,
    ChangePasswordFragment.Listener,
    DeleteItemFragment.Listener,
    CreatePasswordFragment.Listener {

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

    override fun onLogInClick(username: String, password: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onCreateAccountClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreateAccountFragment())
            .commit()
    }

    override fun onSaveAccountClick(username: String, password: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onItemClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InformationFragment.getInstance(id))
            .commit()
    }

    override fun onFABClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreateItemFragment())
            .commit()
    }

    override fun onCreatePasswordClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreatePasswordFragment())
            .commit()
    }

    override fun onSaveItemClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onDeleteClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DeleteItemFragment.getInstance(id))
            .commit()
    }

    override fun onChangeClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ChangePasswordFragment.getInstance(id))
            .commit()
    }

    override fun onOkClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onSaveChangedClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InformationFragment.getInstance(id))
            .commit()
    }

    override fun onNoClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InformationFragment.getInstance(id))
            .commit()
    }

    override fun onYesClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onSavePasswordClick(password: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreateItemFragment.getInstance(password))
            .commit()
    }
}