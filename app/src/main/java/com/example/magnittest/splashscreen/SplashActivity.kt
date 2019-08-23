package com.example.magnittest.splashscreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.magnittest.R
import io.reactivex.functions.Consumer

class SplashActivity : AppCompatActivity() {

    private var myLocationListener: MyLocationListener? = null
    private val REQUEST_CODE_LOCATION = 1
    private var LOCATION_GRANTED = false
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mainPresenter = MainPresenter(this)
        mainPresenter.getTypes()
        myLocationListener = MyLocationListener(this)

        val hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            LOCATION_GRANTED = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
        if (LOCATION_GRANTED) {
            setLocationListener()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LOCATION_GRANTED = true
            }
        }
        if (LOCATION_GRANTED) {
            setLocationListener()
        } else {
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show()
        }
    }

    private fun setLocationListener() {
        myLocationListener!!.setUpLocationListener(
            Consumer { location ->
                Log.e("LOCATION", "ПОЛУЧИЛ ЛОКАЦИЮ")
                mainPresenter.checkShopList(location)
                myLocationListener!!.stop()
            })
    }
}
