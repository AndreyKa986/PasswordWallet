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
        private const val INSTANCE_KEY = "instance_key"
    }

    private val presenter = CreatePasswordPresenter()
    private var actionBar: ActionBar? = null
    private val name by lazy { arguments!!.getString(ID_KEY, "Name") }

    private lateinit var al: AlertDialog
    private lateinit var isItemChecked: BooleanArray
    private var numbers: Int = 0
    private lateinit var t: EditText
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
            savedInstanceState.getBoolean(INSTANCE_KEY)

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
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }

        t = dialogView.findViewById(R.id.num)
        t.setText("$numbers")
        al = builder.create()

        if (isDialogShowing) al.show()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val list = arrayListOf<String>()
                list.add(name)
                list.add("")
                val bundle = bundleOf(ID_KEY to list)
                view.findNavController()
                    .navigate(R.id.action_createPasswordFragment_callback, bundle)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        presenter.setView(this)

        autoCreate.setOnClickListener {
            val nn = t.text.toString().toIntOrNull() ?: 0
            presenter.createPassword(nn, isItemChecked)
        }

        autoSave.setOnClickListener {
            savePassword()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val nn = t.text.toString().toIntOrNull() ?: 0
        presenter.saveSettings(isItemChecked, nn)
        actionBar?.hide()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (al.isShowing) outState.putBoolean(INSTANCE_KEY, true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_pass, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_pass -> {
                al.show()
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
            list.add(name)
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