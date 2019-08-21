package com.example.magnittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.magnittest.dto.Sale
import kotlinx.android.synthetic.main.fragment_sales.*

class SalesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sales, container, false)

        val args = arguments
        val sale = arguments!!.getSerializable("sale") as Sale

        tvSaleName.text = sale.name
//        tvSaleDate.text = sale.startDate
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