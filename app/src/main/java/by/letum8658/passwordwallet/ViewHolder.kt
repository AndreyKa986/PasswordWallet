package by.letum8658.passwordwallet

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val itemName = itemView.findViewById<TextView>(R.id.itemText)

    fun bind(item: Item) {
        itemName.text = item.name
    }
}