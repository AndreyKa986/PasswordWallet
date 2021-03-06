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
import com.andreykapustindev.passwordwallet.presenters.ChangePasswordPresenter
import com.andreykapustindev.passwordwallet.view.views.ChangePasswordView
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment(), ChangePasswordView {

    companion object {

        private const val ID_KEY = "id_key"
        private const val ERROR = 1
        private const val PASSWORD = 2
    }

    private val presenter = ChangePasswordPresenter()
    private lateinit var progressBar: ProgressBar
    private var actionBar: ActionBar? = null

    private val list by lazy { arguments!!.getStringArrayList(ID_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.let {
            list?.apply {
                val titleName = this[0]
                it.title = titleName
            }
            it.show()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bundle = bundleOf(ID_KEY to list)
                view.findNavController()
                    .navigate(R.id.action_changePasswordFragment_callback, bundle)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.change_progress_circular)

        presenter.setView(this)

        changePassword.setText(list!![2])
        confirmPassword.setText(list!![2])
    }

    override fun onSaveClick(list: ArrayList<String>) {
        view?.let {
            val bundle = bundleOf(ID_KEY to list)
            it.findNavController()
                .navigate(R.id.action_changePasswordFragment_to_informationFragment, bundle)
        }
    }

    override fun showMessage(number: Int) {
        when (number) {
            ERROR -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            PASSWORD -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
        }
    }

    override fun progressBarOn() {
        progressBar.visibility = View.VISIBLE
    }

    override fun progressBarOff() {
        progressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_change, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.auto_change -> {
                val bundle = bundleOf(ID_KEY to list)
                view?.apply {
                    this.findNavController()
                        .navigate(R.id.action_changePasswordFragment_to_createPasswordTwoFragment, bundle)
                }
                true
            }
            R.id.save_change -> {
                presenter.saveItem(
                    list!![0],
                    list!![1],
                    changePassword.text.toString(),
                    confirmPassword.text.toString()
                )
                true
            }
            android.R.id.home -> {
                back()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun back() {
        view?.let {
            val bundle = bundleOf(ID_KEY to list)
            it.findNavController()
                .navigate(R.id.action_changePasswordFragment_to_informationFragment, bundle)
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }
}