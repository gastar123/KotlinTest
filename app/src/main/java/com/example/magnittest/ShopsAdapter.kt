package com.example.magnittest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magnittest.dto.Shop
import java.util.ArrayList

class ShopsAdapter(val callback: Callback) : RecyclerView.Adapter<ShopsAdapter.ShopHolder>() {

    val shops: ArrayList<Shop> = ArrayList()

    fun changeData(shops: List<Shop>) {
        this.shops.clear()
        this.shops.addAll(shops)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShopHolder(LayoutInflater.from(parent.context).inflate(R.layout.shop, parent, false))


    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        holder.bind(shops[position])
    }

    override fun getItemCount() = shops.size

    inner class ShopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvWorkTime = itemView.findViewById<TextView>(R.id.tvWorkTime)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        private val ivShop = itemView.findViewById<ImageView>(R.id.ivShop)

        fun bind(shop: Shop) {
            tvName.text = shop.name
            tvWorkTime.text = "${shop.opening} - ${shop.closing}"
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(shops[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(shop: Shop)
    }
}