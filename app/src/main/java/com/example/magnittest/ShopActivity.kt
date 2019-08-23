package com.example.magnittest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.magnittest.dto.Shop
import com.example.magnittest.sales.SalesActivity
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity() {

    private lateinit var shop: Shop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        shop = intent.getSerializableExtra("shop") as Shop

        shopType.text = shop.shopType
        shopName.text = shop.name
        shopAddress.text = shop.address
        shopStatus.text = shop.status
        if (shop.status.equals("Нет информации")) {
            shopTime.text = " - "
        }
        if (shop.opening == null || shop.closing == null) {
            shopTime.text = "-"
        } else if (shop.opening.equals(shop.closing)) {
            shopTime.text = "Круглосуточно"
        } else {
        shopTime.text = "${shop.opening} - ${shop.closing}"
        }
    }

    fun btnMapOnClick(v: View) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("requestCode", "oneShop")
        intent.putExtra("latitude", shop.lat)
        intent.putExtra("longitude", shop.lng)
        intent.putExtra("image", shop.image)
        startActivity(intent)
    }

    fun btnSalesOnClick(v: View) {
        val intent = Intent(this, SalesActivity::class.java)
        intent.putExtra("shopId", shop.id)
        startActivity(intent)
    }
}
