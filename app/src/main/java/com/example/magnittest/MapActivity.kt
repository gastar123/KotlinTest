package com.example.magnittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("2162a9e2-59ee-4bcf-b2ac-eec55ce9b749")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)

        var requestCode = intent.getStringExtra("requestCode")
        var shopList = Model.shopList
        var myLat = Model.myLocation!!.latitude
        var myLng = Model.myLocation!!.longitude
        var image = 0

        if (requestCode.equals("shopList")) {
            var lngList = shopList.map { it.lng }.toDoubleArray()
            var latList = shopList.map { it.lat }.toDoubleArray()

            mapView.map.move(
                CameraPosition(Point(myLat, myLng), 12f, 0.0f, 0.0f)
            )

            mapView.map.mapObjects.addPlacemark(Point(myLat, myLng))

            val cluster = mapView.map.mapObjects.addClusterizedPlacemarkCollection(ClusterListener { cluster ->
                cluster.placemarks
                Log.e("MAP", "${cluster.size}")
            })

            for (i in 0 until shopList.size) {
//                mapView.map.mapObjects.addPlacemark(
//                            Point(latList[i], lngList[i]),
//                    ImageProvider.fromResource(this, R.drawable.yandex_logo_ru)
//                )

                cluster.addPlacemark(
                    Point(latList[i], lngList[i]),
                    ImageProvider.fromResource(this, shopList[i].image))
            }

            cluster.clusterPlacemarks(2.0, 15)

        } else if (requestCode.equals("oneShop")) {
            var latitude = intent.getDoubleExtra("latitude", 0.0)
            var longitude = intent.getDoubleExtra("longitude", 0.0)

            mapView.map.move(
                CameraPosition(Point(latitude, longitude), 15f, 0.0f, 0.0f)
            )

            mapView.map.mapObjects.addPlacemark(
                Point(latitude, longitude),
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
