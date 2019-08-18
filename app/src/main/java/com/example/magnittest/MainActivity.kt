package com.example.magnittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.magnittest.dto.Shop
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var model: Model? = null
    private var shopsAdapter: ShopsAdapter? = null
    private var myLocationListener: MyLocationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        model = Model(this)
        model!!.getShops()
        myLocationListener = MyLocationListener(this)
        myLocationListener!!.setUpLocationListener(
            Consumer { location ->  model!!.checkShopList(location)
            myLocationListener!!.stop()})

        shopsAdapter = ShopsAdapter(object : ShopsAdapter.Callback {
            override fun onItemClicked(shop: Shop) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        rvMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvMain.adapter = shopsAdapter
    }

    fun updateView(shops: List<Shop>) {
        shopsAdapter!!.changeData(shops)
    }

    fun setDistation() {

    }
}
