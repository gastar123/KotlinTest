package com.example.magnittest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.magnittest.dto.Sale
import java.util.*

class SalesPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    val saleList: ArrayList<Sale> = ArrayList()

    fun changeData(saleList: List<Sale>) {
        this.saleList.clear()
        this.saleList.addAll(saleList)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return SalesFragment.newInstance(saleList[position])
    }

    override fun getCount(): Int {
        return saleList.size
    }
}