package com.andreykapustindev.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.andreykapustindev.passwordwallet.R
import com.andreykapustindev.passwordwallet.presenters.CreateAccountPresenter
import com.andreykapustindev.passwordwallet.utils.AppPrefManager
import com.andreykapustindev.passwordwallet.view.views.CreateAccountView
import kotlinx.android.synthetic.main.fragment_create_account.*

class CreateAccountFragment : Fragment(), CreateAccountView {

    companion object {

        private const val TAKEN_NAME = 1
        private const val PASSWORD = 2
        private const val USERNAME = 3
        private const val ERROR = 4
    }

    private val presenter = CreateAccountPresenter()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_createAccountFragment_callback)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.account_progress_circular)

        presenter.setView(this)

        accountSave.setOnClickListener {
            presenter.createAccount(
                accountUsername.text.toString(),
                accountPassword.text.toString(),
                accountConfirm.text.toString()
            )
        }
    }

    override fun getPrefsManager(): AppPrefManager =
        AppPrefManager(context!!)

    override fun onCreateAccountClick() {
        view?.findNavController()
            ?.navigate(R.id.action_createAccountFragment_to_recyclerViewFragment)
    }

    override fun onStop() {
        super.onStop()
        presenter.saveName(accountUsername.text.toString())
    }

    override fun showMessage(number: Int) {
        when (number) {
            TAKEN_NAME -> Toast.makeText(context, R.string.have_user, Toast.LENGTH_SHORT).show()
            PASSWORD -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
            USERNAME -> Toast.makeText(context, R.string.take_name, Toast.LENGTH_SHORT).show()
            ERROR -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
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