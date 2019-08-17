package com.example.magnittest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.magnittest.dto.Shop
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var model: Model? = null
    private var shopsAdapter: ShopsAdapter? = null
    private var myLocationListener = MyLocationListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myLocationListener.setUpLocationListener(
            this,
            Consumer { t -> Toast.makeText(this, "${t?.latitude} - ${t?.longitude}", Toast.LENGTH_LONG).show() })
        init()
    }

    private fun init() {
        model = Model(this)
        model!!.getShops()

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
}
