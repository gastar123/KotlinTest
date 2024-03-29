package com.example.magnittest

import com.example.magnittest.dto.Sale
import com.example.magnittest.dto.Shop
import com.example.magnittest.dto.Type
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface MagnitApi {

    @Headers(
        "version:4",
        "If-Modified-Since: Thu Aug 15 2019 22:38:25 GMT+0300 (MSK)"
    )
    @GET("magnit-api/shops")
    fun getShops(): Observable<List<Shop>>

    @Headers("version:4")
    @GET("magnit-api/shops/types")
    fun getTypes(): Observable<List<Type>>

    @Headers("version:4")
    @GET("magnit-api/discounts/with-publisher")
    fun getSales(@Query("shopId") shopId: Int, @Query("publisher") publisher: String): Observable<List<Sale>>

    companion object NetworkUtils {
        fun create(): MagnitApi {
            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://mobiapp.tander.ru/")
                .build()
            return retrofit.create(MagnitApi::class.java)
        }
    }
}