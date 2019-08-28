package by.letum8658.passwordwallet.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.model.BackPressed
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.ChangePasswordPresenter
import by.letum8658.passwordwallet.view.views.ChangePasswordView
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment(),
    ChangePasswordView, OnBackPressedListener {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(list: ArrayList<String>): ChangePasswordFragment {
            return ChangePasswordFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ID_KEY, list)
                }
            }
        }
    }

    private val presenter = ChangePasswordPresenter()
    private var listener: Listener? = null
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
            presenter.saveItem()
        }
    }

    override fun setName(name: String) {
        changeName.text = name
    }

    override fun getName(): String = changeName.text.toString()

    override fun getPassword(): String = changePassword.text.toString()

    override fun getConfirmPassword(): String = changeConfirm.text.toString()

    override fun saveChange(list: ArrayList<String>) {
        listener?.onSaveChangedClick(list)
    }

    override fun onBackPressed() {
        BackPressed.setList(list)
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        presenter.detach()
    }

    interface Listener {
        fun onSaveChangedClick(list: ArrayList<String>)
    }
}