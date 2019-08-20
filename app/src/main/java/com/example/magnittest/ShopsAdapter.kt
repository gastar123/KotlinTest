package com.example.magnittest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magnittest.dto.Shop
import java.util.*

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

        private var HOUR = Calendar.HOUR_OF_DAY

        fun bind(shop: Shop) {
            val instance = Calendar.getInstance()
            var hour = instance.get(Calendar.HOUR_OF_DAY)
            var minute = instance.get(Calendar.MINUTE) + (hour * 60)
            // Почему-то могут быть null.
            var opening = shop.opening
            var closing = shop.closing
            if (opening != null && closing != null && opening.contains(":") && closing.contains(":")) {
                var open = opening.split(":").map { it.toInt() }
                var close = closing.split(":").map { it.toInt() }
                var openMinute = open[0] * 60 + open[1]
                var closeMinute = close[0] * 60 + close[1]
                Log.e("TIME", "$openMinute - $closeMinute time: $minute")
                if ((minute > openMinute) && (minute < closeMinute)) {
                    tvStatus.text = "Магазин открыт"
                } else if ((closeMinute < openMinute) && ((minute > openMinute) || (minute < closeMinute))) {
                    tvStatus.text = "Магазин открыт"
                } else {
                    tvStatus.text = "Магазин закрыт"
                }
                if (opening.equals(closing)) {
                    tvWorkTime.text = "Круглосуточно"
                    tvStatus.text = "Магазин открыт"
                } else {
                    tvWorkTime.text = "$opening - $closing"
                }
            } else {
                tvStatus.text = "Нет информации"
                tvWorkTime.text = "-"
            }
            shop.status = tvStatus.text.toString()
            tvName.text = shop.name
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(shops[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(shop: Shop)
    }
}