package by.letum8658.passwordwallet.view.fragments

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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.InformationPresenter
import by.letum8658.passwordwallet.view.views.InformationView
import kotlinx.android.synthetic.main.fragment_item_information.*

class InformationFragment : Fragment(), InformationView {

    companion object {

        private const val ID_KEY = "id_key"
        private const val INSTANCE_KEY = "instance_key"
        private const val DIALOG_KEY = "dialog_key"
    }

    private val presenter = InformationPresenter()
    private lateinit var progressBar: ProgressBar
    private lateinit var builder: AlertDialog.Builder
    private var actionBar: ActionBar? = null
    private var isDialogShowing = false
    private lateinit var alertDialog: AlertDialog

    private val item by lazy { arguments!!.getString(ID_KEY, " ") }
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
        return inflater.inflate(R.layout.fragment_item_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState != null) isDialogShowing =
            savedInstanceState.getBoolean(DIALOG_KEY)

        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.let {
            if (item.isNotBlank()) {
                it.title = "    $item"
            } else {
                list?.apply {
                    val titleName = this[0]
                    it.title = "    $titleName"
                }
            }
            it.show()
        }

        builder = AlertDialog.Builder(activity as AppCompatActivity)
        builder.setTitle(R.string.really)
            .setPositiveButton(R.string.yes) { _, _ ->
                isDialogShowing = false
                presenter.deleteItem(item)
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                isDialogShowing = false
                dialog.cancel()
            }

        alertDialog = builder.create()

        if (isDialogShowing) alertDialog.show()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_informationFragment_callback)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.inform_progress_circular)

        presenter.setView(this)

        presenter.showData(item, savedInstanceState?.getStringArrayList(INSTANCE_KEY), list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionBar?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_info, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.change_info -> {
                change()
                true
            }
            R.id.delete_info -> {
                isDialogShowing = true
                alertDialog.show()
                true
            }
            android.R.id.home -> {
                back()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        view?.let {
            val login = informationLogin.text.toString()
            val password = informationPassword.text.toString()
            val list = arrayListOf(login, password)
            outState.putStringArrayList(INSTANCE_KEY, list)
        }
        if (isDialogShowing && alertDialog.isShowing) outState.putBoolean(DIALOG_KEY, true)
    }

    override fun setLogin(name: String) {
        informationLogin.text = name
    }

    override fun setPassword(password: String) {
        informationPassword.text = password
    }

    override fun delete() {
        view?.apply { findNavController().navigate(R.id.action_informationFragment_to_recyclerViewFragment) }
    }

    override fun change() {
        view?.let {
            val login = informationLogin.text.toString()
            val password = informationPassword.text.toString()
            val list = arrayListOf(item, login, password)
            val bundle = bundleOf(ID_KEY to list)
            it.findNavController()
                .navigate(R.id.action_informationFragment_to_changePasswordFragment, bundle)
        }
    }

    private fun back() {
        view?.apply { findNavController().navigate(R.id.action_informationFragment_to_recyclerViewFragment) }
    }

    override fun showMessage() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
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