package com.example.magnittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private var myLocation: PlacemarkMapObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("2162a9e2-59ee-4bcf-b2ac-eec55ce9b749")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)

        var requestCode = intent.getStringExtra("requestCode")
        var shopList = Model.shopList
        var myLat = Model.myLocation!!.latitude
        var myLng = Model.myLocation!!.longitude

        locationManager = MapKitFactory.getInstance().createLocationManager()

        if (requestCode.equals("shopList")) {
            var lngList = shopList.map { it.lng }.toDoubleArray()
            var latList = shopList.map { it.lat }.toDoubleArray()

            mapView.map.move(
                CameraPosition(Point(myLat, myLng), 12f, 0.0f, 0.0f)
            )


            myLocation = mapView.map.mapObjects.addPlacemark(
                Point(myLat, myLng),
                ImageProvider.fromResource(this, R.drawable.me2)
            )

            val cluster = mapView.map.mapObjects.addClusterizedPlacemarkCollection(ClusterListener {
                it.appearance.setIcon(ImageProvider.fromResource(this, R.drawable.magnit_many))
            })

            for (i in 0 until shopList.size) {
                cluster.addPlacemark(
                    Point(latList[i], lngList[i]),
                    ImageProvider.fromResource(this, shopList[i].image)
                )
            }

            cluster.clusterPlacemarks(7.0, 15)

        } else if (requestCode.equals("oneShop")) {
            var latitude = intent.getDoubleExtra("latitude", 0.0)
            var longitude = intent.getDoubleExtra("longitude", 0.0)
            var image = intent.getIntExtra("image", 0)

            mapView.map.move(
                CameraPosition(Point(latitude, longitude), 15f, 0.0f, 0.0f)
            )

            mapView.map.mapObjects.addPlacemark(
                Point(latitude, longitude),
                ImageProvider.fromResource(this, image)
            )
        }
    }

    fun onClick(v: View) {
        locationManager!!.requestSingleUpdate(object : LocationListener {
            override fun onLocationStatusUpdated(p0: LocationStatus) {

            }

            override fun onLocationUpdated(location: Location) {
                val latitude = location.position.latitude
                val longitude = location.position.longitude
                mapView.map.move(
                    CameraPosition(Point(location.position.latitude, location.position.longitude), 15f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 2f),
                    null
                )
                if (myLocation == null) {
                    myLocation = mapView.map.mapObjects.addPlacemark(
                        Point(latitude, longitude),
                        ImageProvider.fromResource(this@MapActivity, R.drawable.me2)
                    )
                } else {
                    myLocation?.geometry = Point(latitude, longitude)
                }
            }
        })
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
