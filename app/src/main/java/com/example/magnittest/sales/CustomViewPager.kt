package com.example.magnittest.sales

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.example.magnittest.dto.Sale
import me.relex.circleindicator.CircleIndicator
import java.util.jar.Attributes

class CustomViewPager(context: Context, attr: AttributeSet) : ViewPager(context, attr) {

//    init {
//        val indicator = CircleIndicator(context)
//        adapter = ViewPagerAdapter()
//        indicator.setViewPager(this)
//    }

    fun setList(saleList: List<Sale>) {
        val indicator = CircleIndicator(context)
        adapter = ViewPagerAdapter(saleList)
        indicator.setViewPager(this)
    }
}