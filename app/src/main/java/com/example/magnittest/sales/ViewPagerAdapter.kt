package com.example.magnittest.sales

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.magnittest.R
import com.example.magnittest.dto.Sale
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_sales.view.*
import kotlinx.android.synthetic.main.view_pager_item.view.*

class ViewPagerAdapter(var saleList: List<Sale>) : PagerAdapter() {

    override fun isViewFromObject(view: View, key: Any): Boolean = view == key as View

    override fun getCount(): Int {
        return saleList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.view_pager_item, container, false)
        Picasso.with(container.context)
            .load("http://mobiapp.tander.ru/magnit-api/images/220/${saleList[position].image}")
            .placeholder(R.drawable.progress_animation)
            .into(view.imageView)
        container.addView(view)
        return view
    }
}