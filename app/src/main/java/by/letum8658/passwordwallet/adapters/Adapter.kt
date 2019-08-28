package by.letum8658.passwordwallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.letum8658.passwordwallet.R

class Adapter(private var items: List<String>, private val listener: ClickListener) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            listener.onItemClick(items[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun itemListBySearch(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onItemClick(item: String)
    }
}