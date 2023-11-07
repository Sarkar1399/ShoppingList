package com.sarkar.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sarkar.shoppinglist.R
import com.sarkar.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopListAdapter.ShopListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListAdapter.ShopListViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled) "Active" else "No active"
        holder.view.setOnLongClickListener {
            true
        }

        if (shopItem.enabled) {
            holder.tvName.text = "${shopItem.name} $status"
            holder.tvCount.text = shopItem.count.toString()
            holder.tvName.setTextColor(
                ContextCompat.getColor(
                    holder.view.context,
                    android.R.color.holo_green_light
                )
            )
        }
    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(
            ContextCompat.getColor(
                holder.view.context,
                android.R.color.white
            )
        )
    }

    override fun getItemCount(): Int = shopList.size

    inner class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvCount = view.findViewById<TextView>(R.id.tvCount)
    }

}