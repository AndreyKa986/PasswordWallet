package by.letum8658.passwordwallet.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.CreateItemPresenter
import by.letum8658.passwordwallet.view.views.CreateItemView
import kotlinx.android.synthetic.main.fragment_create_item.*

class CreateItemFragment : Fragment(), CreateItemView, OnBackPressedListener {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(list: ArrayList<String>): CreateItemFragment {
            return CreateItemFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ID_KEY, list)
                }
            }
        }
    }

    private val presenter = CreateItemPresenter()
    private var listener: Listener? = null
    private lateinit var progressBar: ProgressBar
    private val list by lazy { arguments?.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        progressBar = view.findViewById(R.id.item_progress_circular)

        presenter.setView(this)

        presenter.setData(list)

        itemCreate.setOnClickListener {
            listener?.onCreatePasswordClick(itemName.text.toString())
        }

        itemSave.setOnClickListener {
            presenter.saveItem(
                itemName.text.toString(),
                itemPassword.text.toString(),
                itemConfirm.text.toString()
            )
        }
    }

    override fun setName(name: String) {
        itemName.setText(name)
    }

    override fun setPassword(password: String) {
        itemPassword.setText(password)
    }

    override fun setConfirmPassword(confirm: String) {
        itemConfirm.setText(confirm)
    }

    override fun onSaveItemClick() {
        listener?.onSaveItemClick()
    }

    override fun onBackPressed() {}

    override fun showMessage(number: Int) {
        when (number) {
            1 -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(context, R.string.take_item, Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(context, R.string.have_item, Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
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
        fun onCreatePasswordClick(name: String)
        fun onSaveItemClick()
    }
}