package com.example.magnittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.map.*

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("2162a9e2-59ee-4bcf-b2ac-eec55ce9b749")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)

        var lngList = intent.getDoubleArrayExtra("lngList")
        var latList = intent.getDoubleArrayExtra("latList")
        var myLng = intent.getDoubleExtra("myLng", 0.0)
        var myLat = intent.getDoubleExtra("myLat", 0.0)

        mapView.map.move(
            CameraPosition(Point(myLat, myLng), 12f, 0.0f, 0.0f)
        )

        mapView.map.mapObjects.addPlacemark(
            Point(myLat, myLng),
            ImageProvider.fromResource(this, R.drawable.yandex_logo_en_white))

        for (i in 0 until lngList.size) {
            mapView.map.mapObjects.addPlacemark(
                Point(latList[i], lngList[i]),
                ImageProvider.fromResource(this, R.drawable.yandex_logo_ru)
            )
        }
    }

    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
