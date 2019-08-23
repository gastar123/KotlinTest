package com.example.magnittest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.reactivex.functions.Consumer

class SplashActivity : AppCompatActivity() {

    private var myLocationListener: MyLocationListener? = null
    private val REQUEST_CODE_READ_CONTACTS = 1
    private var READ_CONTACTS_GRANTED = false
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)

        mainPresenter = MainPresenter(this)
        mainPresenter.getTypes()
        myLocationListener = MyLocationListener(this)

        val hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        // если устройство до API 23, устанавливаем разрешение
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true
        } else {
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_READ_CONTACTS
            )
        }
        // если разрешение установлено, загружаем контакты
        if (READ_CONTACTS_GRANTED) {
            myLocationListener!!.setUpLocationListener(
                Consumer { location ->
                    Log.e("LOCATION", "ПОЛУЧИЛ ЛОКАЦИЮ")
                    mainPresenter.checkShopList(location)
                    myLocationListener!!.stop()
                })
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_READ_CONTACTS -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                READ_CONTACTS_GRANTED = true
            }
        }
        if (READ_CONTACTS_GRANTED) {
            myLocationListener!!.setUpLocationListener(
                Consumer { location ->
                    Log.e("LOCATION", "ПОЛУЧИЛ ЛОКАЦИЮ")
                    mainPresenter.checkShopList(location)
                    myLocationListener!!.stop()
                })
        } else {
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show()
        }
    }
}
