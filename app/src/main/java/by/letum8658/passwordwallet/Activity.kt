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

    override fun onLogInClick() {
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

    override fun onSaveAccountClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onItemClick(item: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InformationFragment.getInstance(item))
            .commit()
    }

    override fun onFABClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreateItemFragment())
            .commit()
    }

    override fun onCreatePasswordClick(name: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreatePasswordFragment.getInstance(name))
            .commit()
    }

    override fun onSaveItemClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onDeleteClick(list: ArrayList<String>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DeleteItemFragment.getInstance(list))
            .commit()
    }

    override fun onChangeClick(list: ArrayList<String>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ChangePasswordFragment.getInstance(list))
            .commit()
    }

    override fun onOkClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onSaveChangedClick(list: ArrayList<String>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InformationFragment.getInstance(list))
            .commit()
    }

    override fun onNoClick(list: ArrayList<String>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InformationFragment.getInstance(list))
            .commit()
    }

    override fun onYesClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
    }

    override fun onSavePasswordClick(list: ArrayList<String>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CreateItemFragment.getInstance(list))
            .commit()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var backPressedListener: OnBackPressedListener? = null
        for (fragment in fm.fragments) {
            if (fragment is OnBackPressedListener) {
                backPressedListener = fragment
                break
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed()
            when (backPressedListener) {
                is RecyclerViewFragment -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, LogInFragment())
                    .commit()
                is InformationFragment -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, RecyclerViewFragment())
                    .commit()
                is DeleteItemFragment -> {
                    val list = BackPressed.getList()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, InformationFragment.getInstance(list))
                        .commit()
//                    list.clear()
//                    BackPressed.setList(list)
                }
                is CreatePasswordFragment -> {
                    val list = BackPressed.getList()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, CreateItemFragment.getInstance(list))
                        .commit()
//                    list.clear()
//                    BackPressed.setList(list)
                }
                is CreateItemFragment -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, RecyclerViewFragment())
                    .commit()
                is CreateAccountFragment -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, LogInFragment())
                    .commit()
                is ChangePasswordFragment -> {
                    val list = BackPressed.getList()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, InformationFragment.getInstance(list))
                        .commit()
//                    list.clear()
//                    BackPressed.setList(list)
                }
            }
        } else {
            super.onBackPressed()
        }
    }
}