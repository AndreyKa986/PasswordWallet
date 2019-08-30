package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.presenters.CreateAccountPresenter
import by.letum8658.passwordwallet.utils.AppPrefManager
import by.letum8658.passwordwallet.view.views.CreateAccountView
import kotlinx.android.synthetic.main.fragment_create_account.*

class CreateAccountFragment : Fragment(), CreateAccountView, OnBackPressedListener {

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
        view!!.findNavController()
            .navigate(R.id.action_createAccountFragment_to_recyclerViewFragment)
    }

    override fun onBackPressed() {}

    override fun onStop() {
        super.onStop()
        presenter.saveName(accountUsername.text.toString())
    }

    override fun showMessage(number: Int) {
        when (number) {
            1 -> Toast.makeText(context, R.string.have_user, Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(context, R.string.take_name, Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
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