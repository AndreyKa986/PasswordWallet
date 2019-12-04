package com.andreykapustindev.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.andreykapustindev.passwordwallet.R
import com.andreykapustindev.passwordwallet.presenters.CreateItemPresenter
import com.andreykapustindev.passwordwallet.view.views.CreateItemView
import kotlinx.android.synthetic.main.fragment_create_item.*

class CreateItemFragment : Fragment(), CreateItemView {

    companion object {

        private const val ID_KEY = "id_key"
        private const val PASSWORD = 1
        private const val ITEMNAME = 2
        private const val TAKEN_ITEM = 3
        private const val ERROR = 4
        private const val LOGIN = 5
    }

    private val presenter = CreateItemPresenter()
    private lateinit var progressBar: ProgressBar
    private var actionBar: ActionBar? = null

    private val list by lazy { arguments?.getStringArrayList(ID_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.let {
            it.title = resources.getString(R.string.new_item)
            it.show()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                actionBar?.hide()
                view.findNavController().navigate(R.id.action_createItemFragment_callback)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.item_progress_circular)

        presenter.setView(this)

        presenter.setData(list)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.auto_create -> {
                val name = itemName.text.toString()
                val login = itemLogin.text.toString()
                val list = arrayListOf(name, login)
                val bundle = bundleOf(ID_KEY to list)
                view?.apply {
                    this.findNavController()
                        .navigate(R.id.action_createItemFragment_to_createPasswordFragment, bundle)
                }
                true
            }
            R.id.save_create -> {
                presenter.saveItem(
                    itemName.text.toString(),
                    itemLogin.text.toString(),
                    itemPassword.text.toString(),
                    itemConfirm.text.toString()
                )
                true
            }
            android.R.id.home -> {
                actionBar?.hide()
                back()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setName(name: String) {
        itemName.setText(name)
    }

    override fun setLogin(login: String) {
        itemLogin.setText(login)
    }

    override fun setPassword(password: String) {
        itemPassword.setText(password)
    }

    override fun setConfirmPassword(confirm: String) {
        itemConfirm.setText(confirm)
    }

    override fun onSaveItemClick() {
        view?.findNavController()?.navigate(R.id.action_createItemFragment_to_recyclerViewFragment)
    }

    private fun back() {
        view?.apply { findNavController().navigate(R.id.action_createItemFragment_to_recyclerViewFragment) }
    }

    override fun showMessage(number: Int) {
        when (number) {
            PASSWORD -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
            ITEMNAME -> Toast.makeText(context, R.string.take_item, Toast.LENGTH_SHORT).show()
            TAKEN_ITEM -> Toast.makeText(context, R.string.have_item, Toast.LENGTH_SHORT).show()
            ERROR -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            LOGIN -> Toast.makeText(context, R.string.take_login, Toast.LENGTH_SHORT).show()
        }
    }

    override fun progressBarOn() {
        progressBar.visibility = View.VISIBLE
    }

    override fun progressBarOff() {
        progressBar.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }
}