package com.sarkar.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sarkar.shoppinglist.R

class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tvName)
    val tvCount = view.findViewById<TextView>(R.id.tvCount)
}