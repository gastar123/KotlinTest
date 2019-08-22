package com.example.magnittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.functions.Consumer

class SplashActivity : AppCompatActivity() {

    private var myLocationListener: MyLocationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)

        val mainPresenter = MainPresenter(this)
        mainPresenter.getTypes()
        myLocationListener = MyLocationListener(this)
        myLocationListener!!.setUpLocationListener(
            Consumer { location ->
                Log.e("LOCATION", "ПОЛУЧИЛ ЛОКАЦИЮ")
                mainPresenter.checkShopList(location)
                myLocationListener!!.stop()
            })
    }
}
