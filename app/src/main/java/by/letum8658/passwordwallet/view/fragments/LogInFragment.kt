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
import by.letum8658.passwordwallet.presenters.LogInPresenter
import by.letum8658.passwordwallet.utils.AppPrefManager
import by.letum8658.passwordwallet.view.views.LogInView
import kotlinx.android.synthetic.main.fragment_log_in.*

class LogInFragment : Fragment(), LogInView {

    companion object {

        private const val INCORRECTLY = 1
        private const val DO_NOT_HAVE = 2
        private const val PASSWORD = 3
        private const val USERNAME = 4
        private const val ERROR = 5
    }

    private val presenter = LogInPresenter()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        progressBar = view.findViewById(R.id.log_progress_circular)

        presenter.setView(this)

        presenter.setName()

        logIn.setOnClickListener {
            presenter.logIn(
                logInUsername.text.toString(),
                logInPassword.text.toString()
            )
        }

        logInCreate.setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_createAccountFragment)
        }
    }

    override fun getPrefsManager(): AppPrefManager =
        AppPrefManager(context!!)

    override fun setName(name: String) {
        logInUsername.setText(name)
    }

    override fun onLogInClick() {
        view?.findNavController()?.navigate(R.id.action_logInFragment_to_recyclerViewFragment)
    }

    override fun onStop() {
        super.onStop()
        logInPassword.text.clear()
        presenter.saveName(logInUsername.text.toString())
    }

    override fun showMessage(number: Int) {
        when (number) {
            INCORRECTLY -> Toast.makeText(context, R.string.dont_password, Toast.LENGTH_SHORT).show()
            DO_NOT_HAVE -> Toast.makeText(context, R.string.dont_user, Toast.LENGTH_SHORT).show()
            PASSWORD -> Toast.makeText(context, R.string.take_password, Toast.LENGTH_SHORT).show()
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