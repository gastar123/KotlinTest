package com.example.magnittest

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.magnittest.dto.Shop
import com.yandex.mapkit.MapKitFactory
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var shopsAdapter: ShopsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        shopsAdapter = ShopsAdapter(object : ShopsAdapter.Callback {
            override fun onItemClicked(shop: Shop) {
                val intent = Intent(this@MainActivity, ShopActivity::class.java)
                intent.putExtra("shop", shop)
                startActivity(intent)
            }
        })

        shopsAdapter!!.changeData(Model.shopList)

        rvMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvMain.adapter = shopsAdapter
    }

    fun fabOnClick(v: View) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("requestCode", "shopList")
        startActivity(intent)
    }
}
