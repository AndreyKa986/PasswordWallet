package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.CreatePasswordPresenter
import by.letum8658.passwordwallet.view.views.CreatePasswordView
import kotlinx.android.synthetic.main.fragment_auto_create_password.*

class CreatePasswordFragment : Fragment(), CreatePasswordView {

    companion object {

        private const val ID_KEY = "id_key"
        private const val DIALOG_KEY = "dialog_key"
    }

    private val presenter = CreatePasswordPresenter()
    private var actionBar: ActionBar? = null
    private val arrayList by lazy { arguments?.getStringArrayList(ID_KEY) }

    private lateinit var alertDialog: AlertDialog
    private lateinit var isItemChecked: BooleanArray
    private var numbers: Int = 0
    private lateinit var text: EditText
    private var isDialogShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auto_create_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState != null) isDialogShowing =
            savedInstanceState.getBoolean(DIALOG_KEY)

        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.let {
            it.title = ""
            it.show()
        }

        isItemChecked = presenter.getBooleanArray()
        numbers = presenter.getNumber()
        val checkedNames = resources.getStringArray(R.array.set_strings)

        val builder = AlertDialog.Builder(activity as AppCompatActivity)
        val dialogView = layoutInflater.inflate(R.layout.dialog, null)
        builder.setTitle(R.string.setting)
            .setView(dialogView)
            .setMultiChoiceItems(
                checkedNames,
                isItemChecked
            ) { _, which, isChecked -> isItemChecked[which] = isChecked }
            .setPositiveButton(R.string.ok) { dialog, _ ->
                isDialogShowing = false
                dialog.cancel()
            }

        text = dialogView.findViewById(R.id.num)
        text.setText("$numbers")
        alertDialog = builder.create()

        if (isDialogShowing) alertDialog.show()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val list = arrayListOf<String>()
                list.add(arrayList!![0])
                list.add(arrayList!![1])
                list.add("")
                val bundle = bundleOf(ID_KEY to list)
                view.findNavController()
                    .navigate(R.id.action_createPasswordFragment_callback, bundle)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        presenter.setView(this)

        autoCreate.setOnClickListener {
            numbers = text.text.toString().toIntOrNull() ?: 0
            presenter.createPassword(numbers, isItemChecked)
        }

        autoSave.setOnClickListener {
            savePassword()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        numbers = text.text.toString().toIntOrNull() ?: 0
        presenter.saveSettings(isItemChecked, numbers)
        actionBar?.hide()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isDialogShowing && alertDialog.isShowing) outState.putBoolean(DIALOG_KEY, true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_pass, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_pass -> {
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

    override fun setPassword(password: String) {
        autoPassword.setText(password)
    }

    private fun savePassword() {
        view?.let {
            val list = ArrayList<String>()
            list.add(arrayList!![0])
            list.add(arrayList!![1])
            list.add(autoPassword.text.toString())
            val bundle = bundleOf(ID_KEY to list)
            it.findNavController()
                .navigate(R.id.action_createPasswordFragment_to_createItemFragment, bundle)
        }
    }

    private fun back() {
        view?.apply { findNavController().navigate(R.id.action_createPasswordFragment_to_createItemFragment) }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }
}