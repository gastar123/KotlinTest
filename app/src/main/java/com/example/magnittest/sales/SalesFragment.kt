package com.example.magnittest.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.magnittest.R
import com.example.magnittest.dto.Sale
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_sales.view.*
import java.text.SimpleDateFormat

class SalesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sales, container, false)

        val sale = arguments!!.getSerializable("sale") as Sale

        val format1 = SimpleDateFormat("dd.MM")
        val startDate = format1.format(sale.startDate)
        val endDate = format1.format(sale.endDate)

        view.tvSaleDate.text = "$startDate - $endDate"
        view.tvSaleName.text = sale.name
        view.tvSalePrice.text = sale.price.toString()
        view.tvSaleOldPrice.text = sale.oldPrice.toString()

        Picasso.with(activity)
            .load("http://mobiapp.tander.ru/magnit-api/images/220/${sale.image}")
            .placeholder(R.drawable.progress_animation)
            .into(view.ivSale)

        return view
    }

    companion object {
        fun newInstance(sale: Sale): SalesFragment {
            val args = Bundle()

            args.putSerializable("sale", sale)

            val fragment = SalesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}