package com.example.magnittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.magnittest.dto.Sale
import kotlinx.android.synthetic.main.activity_sales.*

class SalesActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: SalesPagerAdapter
    private lateinit var saleList: List<Sale>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        val shopId = intent.getIntExtra("shopId", 0)
        Model.getSales(shopId, this)

        pagerAdapter = SalesPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
    }

    fun setSaleList(saleList: List<Sale>) {
        this.saleList = saleList
        pagerAdapter.changeData(saleList)
    }
}
