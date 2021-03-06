package com.andreykapustindev.passwordwallet.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andreykapustindev.passwordwallet.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val itemName = itemView.findViewById<TextView>(R.id.itemText)

    fun bind(item: String) {
        itemName.text = item
    }
}