package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.letum8658.passwordwallet.Adapter
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_recyclerview_items.*

class RecyclerViewFragment : Fragment(), RecyclerViewView, Adapter.ClickListener {

    private val presenter = RecyclerViewPresenter()
    private var listener: Listener? = null
    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recyclerview_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val database = presenter.getDataBase()
        adapter = Adapter(database, this)
        recyclerView.adapter = adapter

        search.addTextChangedListener(object : TextWatcher {

            private val handler = Handler(Looper.getMainLooper())
            private lateinit var workRunnable: Runnable

            override fun afterTextChanged(string: Editable?) {
                workRunnable = Runnable { updateList() }
                handler.removeCallbacks(workRunnable)
                handler.postDelayed(workRunnable, 300L)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        FAB.setOnClickListener {
            presenter.fab()
        }
    }

    override fun onItemClick(item: String) {
        listener?.onItemClick(item)
    }

    private fun updateList() {
        adapter.itemListBySearch(presenter.getSearchList())
    }

    override fun getSearchString(): String = search.text.toString()

    override fun fab() {
        listener?.onFABClick()
    }

    override fun updateDatabase() {
        updateList()
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
        fun onItemClick(item: String)
        fun onFABClick()
    }
}