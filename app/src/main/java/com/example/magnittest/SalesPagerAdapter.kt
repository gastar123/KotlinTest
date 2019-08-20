package com.example.magnittest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.magnittest.dto.Sale

class SalesPagerAdapter(fragmentManager: FragmentManager, private val saleList: List<Sale>) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return SalesFragment.newInstance(saleList[position])
    }

    override fun getCount(): Int {
        return saleList.size
    }
}