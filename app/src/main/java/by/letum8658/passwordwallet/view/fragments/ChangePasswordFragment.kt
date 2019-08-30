package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.presenters.ChangePasswordPresenter
import by.letum8658.passwordwallet.view.views.ChangePasswordView
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment(), ChangePasswordView, OnBackPressedListener {

    companion object {

        private const val ID_KEY = "id_key"
    }

    private val presenter = ChangePasswordPresenter()
    private lateinit var progressBar: ProgressBar
    private val list by lazy { arguments!!.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        progressBar = view.findViewById(R.id.change_progress_circular)

        presenter.setView(this)

        presenter.showItem(list!![0])

        changeSave.setOnClickListener {
            presenter.saveItem(
                changeName.text.toString(),
                changePassword.text.toString(),
                changeConfirm.text.toString()
            )
        }
    }

    override fun setName(name: String) {
        changeName.text = name
    }

    override fun onSaveClick(list: ArrayList<String>) {
        val bundle = bundleOf(ID_KEY to list)
        view!!.findNavController()
            .navigate(R.id.action_changePasswordFragment_to_informationFragment, bundle)
    }

    override fun onBackPressed() {
        EntityManager.setList(list)
    }

    override fun showMessage(number: Int) {
        when (number) {
            1 -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
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