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

    private var model: Model? = null
    private var shopsAdapter: ShopsAdapter? = null
    private var myLocationListener: MyLocationListener? = null
    private var shops: List<Shop>? = null
    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        model = Model(this)
        model!!.getTypes()
        myLocationListener = MyLocationListener(this)
        myLocationListener!!.setUpLocationListener(
            Consumer { location ->
                this.location = location
                Log.e("LOCATION", "ПОЛУЧИЛ ЛОКАЦИЮ")
                model!!.checkShopList(location)
                myLocationListener!!.stop()
            })

        shopsAdapter = ShopsAdapter(object : ShopsAdapter.Callback {
            override fun onItemClicked(shop: Shop) {
                val intent = Intent(this@MainActivity, ShopActivity::class.java)
                intent.putExtra("shop", shop)
                startActivity(intent)
            }
        })

        rvMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvMain.adapter = shopsAdapter
    }

    fun updateView(shops: List<Shop>) {
        this.shops = shops
        shopsAdapter!!.changeData(shops)
    }

    fun fabOnClick(v: View) {
        val intent = Intent(this, MapActivity::class.java)
        var lngList = shops!!.map { it.lng }.toDoubleArray()
        var latList = shops!!.map { it.lat }.toDoubleArray()
        intent.putExtra("lngList", lngList)
        intent.putExtra("latList", latList)
        intent.putExtra("myLng", location!!.longitude)
        intent.putExtra("myLat", location!!.latitude)
        startActivity(intent)
    }
}
