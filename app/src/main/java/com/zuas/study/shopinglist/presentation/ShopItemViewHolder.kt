package com.zuas.study.shopinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zuas.study.shopinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName: TextView = itemView.findViewById(R.id.tv_name)
    val tvCount: TextView = itemView.findViewById(R.id.tv_count)
}
