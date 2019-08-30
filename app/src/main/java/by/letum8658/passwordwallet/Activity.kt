package by.letum8658.passwordwallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.model.MainActivityNavigation
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.view.fragments.LogInFragment
import by.letum8658.passwordwallet.view.fragments.CreateAccountFragment
import by.letum8658.passwordwallet.view.fragments.RecyclerViewFragment
import by.letum8658.passwordwallet.view.fragments.CreateItemFragment
import by.letum8658.passwordwallet.view.fragments.InformationFragment
import by.letum8658.passwordwallet.view.fragments.ChangePasswordFragment
import by.letum8658.passwordwallet.view.fragments.DeleteItemFragment
import by.letum8658.passwordwallet.view.fragments.CreatePasswordFragment

class Activity : FragmentActivity(), MainActivityNavigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        if (savedInstanceState == null) {
            replaceFragment(LogInFragment())
        }
    }

    override fun onLogInClick() {
        replaceFragment(RecyclerViewFragment())
    }

    override fun onCreateAccountClick() {
        replaceFragment(CreateAccountFragment())
    }

    override fun onSaveAccountClick() {
        replaceFragment(RecyclerViewFragment())
    }

    override fun onItemClick(item: String) {
        replaceFragment(InformationFragment.getInstance(item))
    }

    override fun onFABClick() {
        replaceFragment(CreateItemFragment())
    }

    override fun onCreatePasswordClick(name: String) {
        replaceFragment(CreatePasswordFragment.getInstance(name))
    }

    override fun onSaveItemClick() {
        replaceFragment(RecyclerViewFragment())
    }

    override fun onDeleteClick(list: ArrayList<String>) {
        replaceFragment(DeleteItemFragment.getInstance(list))
    }

    override fun onChangeClick(list: ArrayList<String>) {
        replaceFragment(ChangePasswordFragment.getInstance(list))
    }

    override fun onOkClick() {
        replaceFragment(RecyclerViewFragment())
    }

    override fun onSaveChangedClick(list: ArrayList<String>) {
        replaceFragment(InformationFragment.getInstance(list))
    }

    override fun onNoClick(list: ArrayList<String>) {
        replaceFragment(InformationFragment.getInstance(list))
    }

    override fun onYesClick() {
        replaceFragment(RecyclerViewFragment())
    }

    override fun onSavePasswordClick(list: ArrayList<String>) {
        replaceFragment(CreateItemFragment.getInstance(list))
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
                is RecyclerViewFragment -> replaceFragment(LogInFragment())
                is InformationFragment -> replaceFragment(RecyclerViewFragment())
                is DeleteItemFragment -> {
                    val list = EntityManager.getList()
                    replaceFragment(InformationFragment.getInstance(list))
                }
                is CreatePasswordFragment -> {
                    val list = EntityManager.getList()
                    replaceFragment(CreateItemFragment.getInstance(list))
                }
                is CreateItemFragment -> replaceFragment(RecyclerViewFragment())
                is CreateAccountFragment -> replaceFragment(LogInFragment())
                is ChangePasswordFragment -> {
                    val list = EntityManager.getList()
                    replaceFragment(InformationFragment.getInstance(list))
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}